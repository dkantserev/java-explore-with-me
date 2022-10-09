package ru.practicum.events.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HTTPclient.Client;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.model.UpdateEventRequest;
import ru.practicum.events.service.user.EventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class EventController {

    private final EventService eventService;
    private final Client client;

    public EventController(EventService eventService, Client client) {
        this.eventService = eventService;
        this.client = client;
    }

    @PostMapping("/{userId}/events")
    public EventDtoGuest addEvent(@PathVariable(name = "userId") Long userId,
                                  @RequestBody EventDto event
    ) {
        return eventService.add(userId, event);
    }

    @PatchMapping("/{userId}/events")
    public UpdateEventRequest editEvent(@PathVariable(name = "userId") Long userId,
                                        @RequestBody UpdateEventRequest event) {
        return eventService.edit(userId, event);
    }

    @GetMapping("/{userId}/events")
    public List<EventDtoGuest> get(@PathVariable(name = "userId") Long userId,
                                   @RequestParam(defaultValue = "0") Long from,
                                   @RequestParam(defaultValue = "10") Long size) {

        return eventService.get(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventDtoGuest get(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "eventId") Long eventId) {
        return eventService.getById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventDto cansel(@PathVariable(name = "userId") Long userId,
                           @PathVariable(name = "eventId") Long eventId) {
        return eventService.cansel(userId, eventId);
    }
}
