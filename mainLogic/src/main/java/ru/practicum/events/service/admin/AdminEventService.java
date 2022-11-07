package ru.practicum.events.service.admin;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.errorApi.exception.EventNotFoundException;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.mapper.EventDtoMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;
import ru.practicum.events.storage.EventStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminEventService {

    private final EventStorage eventStorage;


    public AdminEventService(EventStorage eventStorage) {
        this.eventStorage = eventStorage;

    }


    public List<EventDto> findByParam(List<Long> users, List<State> states, List<Long> categories,
                                      Optional<String> rangeStart, Optional<String> rangeEnd, Long from, Long size) {

        List<EventDto> r = new ArrayList<>();

        if (!users.isEmpty() && !states.isEmpty() && !categories.isEmpty() && rangeEnd.isPresent() &&
                rangeStart.isPresent()) {
            eventStorage.findByAllParam(users, states, categories,
                            LocalDateTime.parse(rangeStart.get(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (!users.isEmpty() && !states.isEmpty() && !categories.isEmpty() && rangeEnd.isPresent() &&
                rangeStart.isEmpty()) {
            eventStorage.findByAllParamMinusStart(users, states, categories,
                    LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (!users.isEmpty() && !states.isEmpty() && !categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByAllParamMinusStartMinusEnd(users, states, categories)
                    .forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (!users.isEmpty() && !states.isEmpty() && categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByAllParamMinusStartMinusEndMinusCategories(users, states)
                    .forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (!users.isEmpty() && states.isEmpty() && categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByAllParamMinusStartMinusEndMinusCategoriesMinusStates(users)
                    .forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (users.isEmpty() && !states.isEmpty() && categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByStates(states).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (users.isEmpty() && states.isEmpty() && !categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByCategories(categories).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (users.isEmpty() && states.isEmpty() && categories.isEmpty() && rangeEnd.isPresent() &&
                rangeStart.isEmpty()) {
            eventStorage.findByEnd(LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss"))).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (users.isEmpty() && states.isEmpty() && categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isPresent()) {
            eventStorage.findByStart(LocalDateTime.parse(rangeStart.get(), DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss"))).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (users.isEmpty() && states.isEmpty() && categories.isEmpty() && rangeEnd.isPresent() &&
                rangeStart.isPresent()) {
            eventStorage.findByStartAndEnd(LocalDateTime.parse(rangeStart.get(), DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss")), LocalDateTime.parse(rangeEnd.get(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (users.isEmpty() && !states.isEmpty() && !categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByStatesAndCategories(states, categories).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (!users.isEmpty() && states.isEmpty() && !categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByUsersAndCategories(users, categories).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }
        if (!users.isEmpty() && !states.isEmpty() && categories.isEmpty() && rangeEnd.isEmpty() &&
                rangeStart.isEmpty()) {
            eventStorage.findByUsersAndState(users, states).forEach(o -> r.add(EventDtoMapper.toDto(o)));

        }

        return r.stream().skip(from).limit(size).collect(Collectors.toList());

    }


    public EventDto edit(EventDto eventDto, Long eventId) {
        var event = eventStorage.findById(eventId).orElseThrow(RuntimeException::new);
        event.setAnnotation(eventDto.getAnnotation());
        event.setCategory(eventDto.getCategory());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setTitle(eventDto.getTitle());
        event.setRequestModeration(eventDto.getRequestModeration());
        if (eventDto.getLocation() != null) {
            event.setLocation(EventDtoMapper.toLocation(eventDto.getLocation()));
        }
        eventStorage.saveAndFlush(event);
        return EventDtoMapper.toDto(eventStorage.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("event " + eventId + "user not found")));
    }

    public EventDto publish(Long eventId) {
        var event = eventStorage.findById(eventId).orElseThrow(RuntimeException::new);
        event.setState(State.PUBLISHED);
        eventStorage.saveAndFlush(event);
        return EventDtoMapper.toDto(eventStorage.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("event " + eventId + "user not found")));
    }

    public EventDto reject(Long eventId) {
        var event = eventStorage.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("event " + eventId + "user not found"));
        event.setState(State.CANCELED);
        eventStorage.saveAndFlush(event);
        return EventDtoMapper.toDto(eventStorage.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("event " + eventId + "user not found")));
    }

    public List<EventDto> undefined() {
        List<Event> r = eventStorage.undefinde(State.PENDING);
        r.forEach(o -> o.setState(State.PUBLISHED));
        eventStorage.flush();
        List<EventDto> q = new ArrayList<>();
        r.forEach(o -> q.add(EventDtoMapper.toDto(o)));
        return q;
    }
}
