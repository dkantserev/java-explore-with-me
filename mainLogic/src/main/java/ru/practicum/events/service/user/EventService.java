package ru.practicum.events.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.storage.CategoryStorage;
import ru.practicum.errorApi.exception.CategoryNotFound;
import ru.practicum.errorApi.exception.EventNotFoundException;
import ru.practicum.errorApi.exception.LocationNotFoundException;
import ru.practicum.errorApi.exception.UserNotFoundException;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.mapper.EventDtoMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.events.model.State;
import ru.practicum.events.model.UpdateEventRequest;
import ru.practicum.events.storage.EventStorage;
import ru.practicum.events.storage.LocationStorage;
import ru.practicum.geocoding.geoService.LocationService;
import ru.practicum.users.storage.UserStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private final EventStorage eventStorage;
    private final LocationStorage locationStorage;
    private final UserStorage userStorage;
    private final CategoryStorage categoryStorage;
    private final LocationService locationService;

    public EventService(EventStorage eventStorage, LocationStorage locationStorage, UserStorage userStorage,
                        CategoryStorage categoryStorage, LocationService locationService) {
        this.eventStorage = eventStorage;
        this.locationStorage = locationStorage;
        this.userStorage = userStorage;
        this.categoryStorage = categoryStorage;
        this.locationService = locationService;
    }

    public EventDtoGuest add(Long userId, EventDto event) {

        if (locationStorage.findIdByCoordinates(event.getLocation().getLat(),
                event.getLocation().getLon()).isEmpty()) {
            locationStorage.save(EventDtoMapper.toLocation(event.getLocation()));
        }
        Long id = eventStorage.save(EventDtoMapper.toModel(event)).getId();
        Event e = eventStorage.findById(id).orElseThrow(() -> new EventNotFoundException("event " + id + "not found"));
        e.setUser(userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("User " + userId +
                "not found")));
        e.setLocation(locationStorage.findIdByCoordinates(event.getLocation().getLat(), event.getLocation().getLon())
                .orElseThrow(() -> new LocationNotFoundException("location not found")));
        eventStorage.saveAndFlush(e);
        var r = EventDtoMapper.toDtoGuest(eventStorage.findById(id).orElseThrow(RuntimeException::new));
        r.setCategory(categoryStorage.findById(e.getCategory())
                .orElseThrow(() -> new CategoryNotFound("Category with id=" + e.getCategory() + " was not found.")));

        return r;
    }

    public UpdateEventRequest edit(Long userId, UpdateEventRequest event) {
        Event updateEvent = eventStorage.findById(event.getEventId()).orElseThrow(RuntimeException::new);
        updateEvent.setAnnotation(event.getAnnotation());
        updateEvent.setCategory(event.getCategory());
        updateEvent.setDescription(event.getDescription());
        updateEvent.setEventDate(LocalDateTime.parse(event.getEventDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        updateEvent.setPaid(event.getPaid());
        updateEvent.setParticipantLimit(event.getParticipantLimit());
        updateEvent.setTitle(event.getTitle());
        if (event.getLocation() != null) {
            updateEvent.setLocation(event.getLocation());
        }
        eventStorage.saveAndFlush(updateEvent);
        return EventDtoMapper.toUpdateDto(eventStorage.findById(event.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event with id=" + event.getEventId() +
                        " was not found.")));

    }

    public List<EventDtoGuest> get(Long userId, Long from, Long size) {
        List<EventDtoGuest> r = new ArrayList<>();
        eventStorage.findByUserId(userId, from).forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        for (EventDtoGuest eventDtoGuest : r) {
            eventDtoGuest.setCategory(categoryStorage.findById(eventStorage.findById(eventDtoGuest.getId())
                    .orElseThrow(() -> new EventNotFoundException("Event with id=" + eventDtoGuest.getId() +
                            " was not found.")).getCategory()).orElseThrow());
        }
        return r.stream().limit(size).collect(Collectors.toList());
    }

    public EventDtoGuest getById(Long userId, Long eventId) {
        var r = EventDtoMapper.toDtoGuest(eventStorage.findByUserIdAndEventId(userId, eventId));
        r.setCategory(categoryStorage.findById(eventStorage.findByUserIdAndEventId(userId, eventId).getCategory())
                .orElseThrow(() -> new EventNotFoundException("Event with id=" + eventId + " was not found.")));
        return r;
    }

    public EventDto cansel(Long userId, Long eventId) {
        if (Objects.equals(eventStorage.findById(eventId).orElseThrow().getUser().getId(), userId) &&
                eventStorage.findById(eventId)
                        .orElseThrow(() -> new EventNotFoundException("Event with id=" + eventId + " was not found."))
                        .getState() == State.PENDING) {
            var r = eventStorage.findById(eventId).orElseThrow();
            r.setState(State.CANCELED);
            eventStorage.save(r);
            return EventDtoMapper.toDto(eventStorage.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException("Event with id=" + eventId + " was not found.")));
        }
        throw new RuntimeException();
    }

    public List<EventDto> findNearbyByAddress(String city, String street, String number, Float distance) {
        LocationShort location = locationService.toCoordinate(city, street, number);
        List<EventDto> returnList = new ArrayList<>();
        List<Location> locationList = locationStorage.searchLocationByFunctionDistance(location.getLat(), location.getLon(),
                distance);
        eventStorage.findByLocation(locationList).forEach(o -> returnList.add(EventDtoMapper.toDto(o)));
        return returnList;
    }

    public List<EventDto> searchFindNearbyByCoordinate(Optional<Float> lat, Optional<Float> lon, float distance) {

        if (lat.isEmpty() || lon.isEmpty()) {
            throw new RuntimeException("bad param");
        }

        List<EventDto> returnList = new ArrayList<>();
        List<Location> locationList = locationStorage.searchLocationByFunctionDistance(lat.get(), lon.get(),
                distance);
        eventStorage.findByLocation(locationList).forEach(o -> returnList.add(EventDtoMapper.toDto(o)));
        return returnList;
    }
}
