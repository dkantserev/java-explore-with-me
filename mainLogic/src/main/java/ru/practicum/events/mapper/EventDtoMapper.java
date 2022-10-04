package ru.practicum.events.mapper;

import ru.practicum.events.dto.EventDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.UpdateEventRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class EventDtoMapper {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT);

    public static Event toModel(EventDto dto) {
        var event = new Event();
        event.setId(dto.getEventId());
        event.setAnnotation(dto.getAnnotation());
        event.setCategory(dto.getCategory());
        event.setLocation(dto.getLocation());
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
                .eventId(event.getId())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .location(event.getLocation())
                .description(event.getDescription())
                .eventDate(event.getEventDate().toString())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())

                .build();
    }

    public static UpdateEventRequest toUpdateDto(Event event) {

        var update = new UpdateEventRequest();
        update.setEventId(event.getId());
        update.setAnnotation(event.getAnnotation());
        update.setCategory(event.getCategory());
        update.setDescription(event.getDescription());
        update.setEventDate(event.getEventDate().toString());
        update.setPaid(event.getPaid());
        update.setParticipantLimit(event.getParticipantLimit());
        update.setTitle(event.getTitle());
        update.setLocation(event.getLocation());


        return update;
    }
}


