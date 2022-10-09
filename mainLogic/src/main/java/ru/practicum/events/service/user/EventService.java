package ru.practicum.events.service.user;

import org.springframework.stereotype.Service;
import ru.practicum.categories.storage.CategoryStorage;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.mapper.EventDtoMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;
import ru.practicum.events.model.UpdateEventRequest;
import ru.practicum.events.storage.EventStorage;
import ru.practicum.events.storage.LocationStorage;
import ru.practicum.users.storage.UserStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventService {

    final private EventStorage eventStorage;
    final private LocationStorage locationStorage;
    final private UserStorage userStorage;
    final private CategoryStorage categoryStorage;

    public EventService(EventStorage eventStorage, LocationStorage locationStorage, UserStorage userStorage,
                        CategoryStorage categoryStorage) {
        this.eventStorage = eventStorage;
        this.locationStorage = locationStorage;
        this.userStorage = userStorage;
        this.categoryStorage = categoryStorage;
    }

    public EventDtoGuest add(Long userId, EventDto event) {

        if (locationStorage.findIdByCoordinates(event.getLocation().getLat(),
                event.getLocation().getLon()).isEmpty()) {
            locationStorage.save(EventDtoMapper.toLocation(event.getLocation()));
        }
        Long id = eventStorage.save(EventDtoMapper.toModel(event)).getId();
        Event e = eventStorage.findById(id).orElseThrow(RuntimeException::new);
        e.setUser(userStorage.findById(userId).orElseThrow(RuntimeException::new));
        e.setLocation(locationStorage.findIdByCoordinates(event.getLocation().getLat(), event.getLocation().getLon())
                .orElseThrow(RuntimeException::new));
        eventStorage.saveAndFlush(e);
        var r = EventDtoMapper.toDtoGuest(eventStorage.findById(id).orElseThrow(RuntimeException::new));
        r.setCategory(categoryStorage.findById(e.getCategory()).orElseThrow());

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
        return EventDtoMapper.toUpdateDto(eventStorage.findById(event.getEventId()).orElseThrow(RuntimeException::new));

    }

    public List<EventDtoGuest> get(Long userId, Long from, Long size) {
        List<EventDtoGuest> r = new ArrayList<>();
        eventStorage.findByUserId(userId, from).forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        for (EventDtoGuest eventDtoGuest : r) {
            eventDtoGuest.setCategory(categoryStorage.findById(eventStorage.findById(eventDtoGuest.getId())
                    .orElseThrow().getCategory()).orElseThrow());
        }
        return r.stream().limit(size).collect(Collectors.toList());
    }

    public EventDtoGuest getById(Long userId, Long eventId) {
        var r = EventDtoMapper.toDtoGuest(eventStorage.findByUserIdAndEventId(userId, eventId));
        r.setCategory(categoryStorage.findById(eventStorage.findByUserIdAndEventId(userId, eventId).getCategory())
                .orElseThrow());
        return r;
    }

    public EventDto cansel(Long userId, Long eventId) {
        if (Objects.equals(eventStorage.findById(eventId).orElseThrow().getUser().getId(), userId) &&
                eventStorage.findById(eventId).orElseThrow().getState() == State.PENDING) {
            var r = eventStorage.findById(eventId).orElseThrow();
            r.setState(State.CANCELED);
            eventStorage.save(r);
            return EventDtoMapper.toDto(eventStorage.findById(eventId).orElseThrow());
        }
        throw new RuntimeException();
    }
}
