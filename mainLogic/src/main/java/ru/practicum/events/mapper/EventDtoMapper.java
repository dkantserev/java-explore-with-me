package ru.practicum.events.mapper;

import ru.practicum.events.dto.EventDto;

import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.events.model.UpdateEventRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class EventDtoMapper {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT);

    public static Event toModel(EventDto dto) {
        var event = new Event();
        event.setId(dto.getId());
        event.setAnnotation(dto.getAnnotation());
        event.setCategory(dto.getCategory());
        event.setDescription(dto.getDescription());
        event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DATE_FORMAT));
        event.setPaid(dto.getPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.getRequestModeration());
        event.setTitle(dto.getTitle());

        return event;
    }

    public static EventDto toDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .location(EventDtoMapper.locationDto(event.getLocation()))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DATE_FORMAT))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .state(event.getState())
                .initiator(event.getUser())
                .views(event.getViews())
                .build();
    }

    public static EventDtoGuest toDtoGuest(Event event) {
        return EventDtoGuest.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .location(EventDtoMapper.locationDto(event.getLocation()))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DATE_FORMAT))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .state(event.getState())
                .initiator(event.getUser())
                .views(event.getViews())
                .build();
    }


    public static UpdateEventRequest toUpdateDto(Event event) {

        var update = new UpdateEventRequest();
        update.setEventId(event.getId());
        update.setAnnotation(event.getAnnotation());
        update.setCategory(event.getCategory());
        update.setDescription(event.getDescription());
        update.setEventDate(event.getEventDate().format(DATE_FORMAT));
        update.setPaid(event.getPaid());
        update.setParticipantLimit(event.getParticipantLimit());
        update.setTitle(event.getTitle());
        update.setLocation(event.getLocation());
        update.setState(event.getState());


        return update;
    }

    public static LocationDto locationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public static Location toLocation(LocationDto dto) {
        var location = new Location();
        location.setId(dto.getId());
        location.setLat(dto.getLat());
        location.setLon(dto.getLon());
        return location;
    }
}


