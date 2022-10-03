package ru.practicum.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/{userId}/requests")
    public RequestDto add(@RequestBody RequestDto dto,
                          @PathVariable(name="userId") Long userId){
        return requestService.add(dto,userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cansel (@PathVariable(name="userId") Long userId,
                              @PathVariable(name="requestId") Long requestId){
        return requestService.cansel(userId,requestId);
    }
}
