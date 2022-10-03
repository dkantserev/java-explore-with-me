package ru.practicum.request.mapper;

import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;


public class MapperRequestDto {

    public static Request toModel(RequestDto dto){
        var request = new Request();
        request.setId(dto.getId());
        request.setCreated(dto.getCreated());
        request.setEvent(dto.getEvent());
        request.setRequester(dto.getRequester());
        request.setStatus(dto.getStatus());
        return request;
    }

    public static RequestDto toDto(Request request){
        return RequestDto.builder()
                .id(request.getId())
                .event(request.getEvent())
                .requester(request.getRequester())
                .created(request.getCreated())
                .status(request.getStatus())
                .build();
    }
}
