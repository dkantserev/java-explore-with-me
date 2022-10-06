package ru.practicum.events.service.user;

import org.springframework.stereotype.Service;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.mapper.EventDtoMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.UpdateEventRequest;
import ru.practicum.events.storage.EventStorage;
import ru.practicum.events.storage.LocationStorage;
import ru.practicum.users.storage.UserStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    final private EventStorage eventStorage;
    final private LocationStorage locationStorage;
    final private UserStorage userStorage;

    public EventService(EventStorage eventStorage, LocationStorage locationStorage, UserStorage userStorage) {
        this.eventStorage = eventStorage;
        this.locationStorage = locationStorage;
        this.userStorage = userStorage;
    }

    public EventDto add(Long userId, EventDto event) {

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

        return EventDtoMapper.toDto(eventStorage.findById(id).orElseThrow(RuntimeException::new));
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

    public List<EventDto> get(Long userId, Long from, Long size) {
        List<EventDto> r = new ArrayList<>();

        eventStorage.findByUserId(userId, from).forEach(o -> r.add(EventDtoMapper.toDto(o)));
        return r.stream().limit(size).collect(Collectors.toList());
    }

    public EventDto getById(Long userId, Long eventId) {
        return EventDtoMapper.toDto(eventStorage.findByUserIdAndEventId(userId, eventId));
    }
}
