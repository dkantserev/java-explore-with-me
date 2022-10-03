package ru.practicum.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.events.storage.EventStorage;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.mapper.MapperRequestDto;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.Status;
import ru.practicum.request.storage.RequestStorage;

import java.util.Objects;

@Service
public class RequestService {
    private final RequestStorage requestStorage;
    private final EventStorage eventStorage;

    public RequestService(RequestStorage requestStorage, EventStorage eventStorage) {
        this.requestStorage = requestStorage;
        this.eventStorage = eventStorage;
    }

    public RequestDto add(RequestDto dto, Long userId) {
        var event = eventStorage.findById(dto.getEvent()).orElseThrow(RuntimeException::new);
        Request request = MapperRequestDto.toModel(dto);
        request.setEventM(event);
        //event.getRequestList().add(request);
        return MapperRequestDto.toDto(requestStorage.save(request));


    }

    public RequestDto cansel(Long userId, Long requestId) {
        var request= requestStorage.findById(requestId).orElseThrow(RuntimeException::new);
        if(Objects.equals(request.getRequester(), userId)){
            request.setStatus(Status.CANSEL);
            requestStorage.save(request);
            requestStorage.flush();
            return MapperRequestDto.toDto(requestStorage.findById(requestId).orElseThrow(RuntimeException::new));
        }
        throw new RuntimeException();
    }
}
