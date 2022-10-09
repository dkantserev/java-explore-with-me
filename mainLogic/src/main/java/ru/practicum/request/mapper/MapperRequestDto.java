package ru.practicum.request.mapper;

import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MapperRequestDto {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Request toModel(RequestDto dto) {
        var request = new Request();
        request.setId(dto.getId());
        request.setCreated(LocalDateTime.parse(dto.getCreated(), DateTimeFormatter.ofPattern(FORMAT)));
        request.setEvent(dto.getEvent());
        request.setRequester(dto.getRequester());
        request.setStatus(dto.getStatus());
        return request;
    }

    public static RequestDto toDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .event(request.getEvent())
                .requester(request.getRequester())
                .created(request.getCreated().format(DateTimeFormatter.ofPattern(FORMAT)))
                .status(request.getStatus())
                .build();
    }
}
