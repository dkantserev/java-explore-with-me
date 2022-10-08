package ru.practicum.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/{userId}/requests")
    public RequestDto add(@RequestParam(name = "eventId") Long eventId,
                          @PathVariable(name = "userId") Long userId) {
        return requestService.add(eventId, userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cansel(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "requestId") Long requestId) {
        return requestService.cansel(userId, requestId);
    }

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getById(@PathVariable(name = "userId") Long userId) {
        return requestService.getByUserId(userId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getByUserByEvents(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "eventId") Long eventId) {
        return requestService.getByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirm(@PathVariable(name = "userId") Long userId,
                              @PathVariable(name = "eventId") Long eventId,
                              @PathVariable(name = "reqId") Long reqId){
        return requestService.confirm(userId,eventId,reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto reject(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "eventId") Long eventId,
                             @PathVariable(name = "reqId") Long reqId){
        return requestService.reject(userId,eventId,reqId);
    }
}
