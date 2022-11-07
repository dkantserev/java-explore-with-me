package ru.practicum.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

import javax.servlet.http.HttpServletRequest;
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
                          @PathVariable(name = "userId") Long userId,
                          HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString());
        return requestService.add(eventId, userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cansel(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "requestId") Long requestId,
                             HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString());
        return requestService.cansel(userId, requestId);
    }

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getById(@PathVariable(name = "userId") Long userId,
                                    HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString());
        return requestService.getByUserId(userId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getByUserByEvents(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "eventId") Long eventId,
                                              HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString());
        return requestService.getByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirm(@PathVariable(name = "userId") Long userId,
                              @PathVariable(name = "eventId") Long eventId,
                              @PathVariable(name = "reqId") Long reqId,
                              HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString());
        return requestService.confirm(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto reject(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "eventId") Long eventId,
                             @PathVariable(name = "reqId") Long reqId,
                             HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString());
        return requestService.reject(userId, eventId, reqId);
    }
}
