package ru.practicum.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.events.storage.EventStorage;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.mapper.MapperRequestDto;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.Status;
import ru.practicum.request.storage.RequestStorage;
import ru.practicum.users.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RequestService {
    private final RequestStorage requestStorage;
    private final EventStorage eventStorage;
    final private UserStorage userStorage;

    public RequestService(RequestStorage requestStorage, EventStorage eventStorage, UserStorage userStorage) {
        this.requestStorage = requestStorage;
        this.eventStorage = eventStorage;
        this.userStorage = userStorage;
    }

    public RequestDto add(Long eventId, Long userId) {
        if (!eventStorage.findById(eventId).orElseThrow().getAvailable()) {
            var event = eventStorage.findById(eventId).orElseThrow(RuntimeException::new);
            var request = new Request();
            request.setEvent(eventId);
            request.setEventM(event);
            request.setRequester(userId);
            request.setCreated(LocalDateTime.now());
            request.setStatus(Status.PENDING);
            Long id = requestStorage.save(request).getId();
            requestStorage.flush();
            if (eventStorage.findById(eventId).orElseThrow().getRequestList().size()
                    == eventStorage.findById(eventId).orElseThrow().getParticipantLimit()) {
                eventStorage.findById(eventId).orElseThrow().setAvailable(true);
            }
            return MapperRequestDto.toDto(requestStorage.findById(id).orElseThrow());
        }
        throw new RuntimeException();
    }

    public RequestDto cansel(Long userId, Long requestId) {
        var request = requestStorage.findById(requestId).orElseThrow(RuntimeException::new);
        if (Objects.equals(request.getRequester(), userId)) {
            request.setStatus(Status.CANCELED);
            requestStorage.save(request);
            requestStorage.flush();
            if (eventStorage.findById(request.getEvent()).orElseThrow().getRequestList().size()
                    == eventStorage.findById(request.getEvent()).orElseThrow().getParticipantLimit()) {
                eventStorage.findById(request.getEvent()).orElseThrow().setAvailable(false);
            }
            return MapperRequestDto.toDto(requestStorage.findById(requestId).orElseThrow(RuntimeException::new));
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
            var r = requestStorage.findById(reqId).orElseThrow();
            r.setStatus(Status.CONFIRMED);
            return MapperRequestDto.toDto(r);
        }
        throw new RuntimeException();
    }

    public RequestDto reject(Long userId, Long eventId, Long reqId) {
        if (Objects.equals(eventStorage.findById(eventId).orElseThrow().getUser().getId(), userId)) {
            var r = requestStorage.findById(reqId).orElseThrow();
            r.setStatus(Status.REJECTED);
            return MapperRequestDto.toDto(r);
        }
        throw new RuntimeException();
    }
}
