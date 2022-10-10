package ru.practicum.request.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.errorApi.exception.EventNotFoundException;
import ru.practicum.errorApi.exception.RequestNotFoundException;
import ru.practicum.events.storage.EventStorage;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.mapper.MapperRequestDto;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.Status;
import ru.practicum.request.storage.RequestStorage;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RequestService {
    private final RequestStorage requestStorage;
    private final EventStorage eventStorage;


    public RequestService(RequestStorage requestStorage, EventStorage eventStorage) {
        this.requestStorage = requestStorage;
        this.eventStorage = eventStorage;

    }

    public RequestDto add(Long eventId, Long userId) {
        if (!eventStorage.findById(eventId).orElseThrow().getAvailable()) {
            var event = eventStorage.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException("Event with id=" + eventId + " was not found."));
            var request = new Request();
            request.setEvent(eventId);
            request.setEventM(event);
            request.setRequester(userId);
            request.setCreated(LocalDateTime.now());
            request.setStatus(Status.PENDING);
            Long id = requestStorage.save(request).getId();
            requestStorage.flush();
            if (eventStorage.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException("Event with id=" + eventId + " was not found."))
                    .getRequestList().size()
                    == eventStorage.findById(eventId).orElseThrow().getParticipantLimit()) {
                eventStorage.findById(eventId)
                        .orElseThrow(() -> new EventNotFoundException("Event with id=" + eventId + " was not found."))
                        .setAvailable(true);
            }
            return MapperRequestDto.toDto(requestStorage.findById(id)
                    .orElseThrow(() -> new RequestNotFoundException("Request with id=" + id + " was not found.")));
        }
        throw new RuntimeException();
    }

    public RequestDto cansel(Long userId, Long requestId) {
        var request = requestStorage.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request with id=" + requestId + " was not found."));
        if (Objects.equals(request.getRequester(), userId)) {
            request.setStatus(Status.CANCELED);
            requestStorage.save(request);
            requestStorage.flush();
            if (eventStorage.findById(request.getEvent()).orElseThrow().getRequestList().size()
                    == eventStorage.findById(request.getEvent()).orElseThrow().getParticipantLimit()) {
                eventStorage.findById(request.getEvent()).orElseThrow().setAvailable(false);
            }
            return MapperRequestDto.toDto(requestStorage.findById(requestId)
                    .orElseThrow(() -> new RequestNotFoundException("Request with id=" + requestId +
                            " was not found.")));
        }
        throw new RuntimeException();
    }

    public List<RequestDto> getByUserId(Long userId) {
        List<RequestDto> r = new ArrayList<>();
        requestStorage.findByRequesterId(userId).forEach(o -> r.add(MapperRequestDto.toDto(o)));
        return r;
    }

    public List<RequestDto> getByUserIdAndEventId(Long userId, Long eventId) {
        var r = new ArrayList<RequestDto>();
        requestStorage.findByRequesterIdAndEvent(userId, eventId).forEach(o -> r.add(MapperRequestDto.toDto(o)));
        return r;
    }

    public RequestDto confirm(Long userId, Long eventId, Long reqId) {
        if (Objects.equals(eventStorage.findById(eventId).orElseThrow().getUser().getId(), userId) &&
                !eventStorage.findById(eventId).orElseThrow().getAvailable()) {
            var r = requestStorage.findById(reqId)
                    .orElseThrow(() -> new RequestNotFoundException("Request with id=" + reqId + " was not found."));
            r.setStatus(Status.CONFIRMED);
            return MapperRequestDto.toDto(r);
        }
        throw new RuntimeException();
    }

    public RequestDto reject(Long userId, Long eventId, Long reqId) {
        if (Objects.equals(eventStorage.findById(eventId).orElseThrow().getUser().getId(), userId)) {
            var r = requestStorage.findById(reqId)
                    .orElseThrow(() -> new RequestNotFoundException("Request with id=" + reqId + " was not found."));
            r.setStatus(Status.REJECTED);
            return MapperRequestDto.toDto(r);
        }
        throw new RuntimeException();
    }
}
