package ru.practicum.events.controller.guest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HTTPclient.Client;

import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.service.GuestEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/events")
public class GuestEventController {

    private final GuestEventService service;
    private final Client client;

    public GuestEventController(GuestEventService service, Client client) {
        this.service = service;
        this.client = client;
    }

    @GetMapping
    public List<EventDtoGuest> getForGuest(@RequestParam(name = "text") Optional<String> text,
                                           @RequestParam(name = "categories", defaultValue = "") List<Long> categories,
                                           @RequestParam(name = "paid") Optional<Boolean> paid,
                                           @RequestParam(name = "rangeStart") Optional<String> rangeStart,
                                           @RequestParam(name = "rangeEnd") Optional<String> rangeEnd,
                                           @RequestParam(name = "onlyAvailable") Optional<Boolean> onlyAvailable,
                                           @RequestParam(name = "sort") Optional<String> sort,
                                           @RequestParam(name = "from", defaultValue = "0") Long from,
                                           @RequestParam(name = "size", defaultValue = "10") Long size,
                                           HttpServletRequest request) {
        client.postStats(request.getRequestURI(), request.getRemoteAddr());
        return service.get(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }


    @GetMapping("/{id}")
    public EventDtoGuest getById(@PathVariable(name = "id") Long id,
                                 HttpServletRequest request) {
        client.postStats(request.getRequestURI(), request.getRemoteAddr());
        return service.getById(id);
    }
}
