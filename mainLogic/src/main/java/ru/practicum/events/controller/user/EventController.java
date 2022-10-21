package ru.practicum.events.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;
import ru.practicum.events.model.UpdateEventRequest;
import ru.practicum.events.service.user.EventService;
import ru.practicum.geocoding.geoService.LocationService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class EventController {

    private final EventService eventService;



    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/{userId}/events")
    public EventDtoGuest addEvent(@PathVariable(name = "userId") Long userId,
                                  @RequestBody EventDto event,
                                  HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return eventService.add(userId, event);
    }

    @PatchMapping("/{userId}/events")
    public UpdateEventRequest editEvent(@PathVariable(name = "userId") Long userId,
                                        @RequestBody UpdateEventRequest event,
                                        HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return eventService.edit(userId, event);
    }

    @GetMapping("/{userId}/events")
    public List<EventDtoGuest> get(@PathVariable(name = "userId") Long userId,
                                   @RequestParam(defaultValue = "0") Long from,
                                   @RequestParam(defaultValue = "10") Long size,
                                   HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return eventService.get(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventDtoGuest get(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "eventId") Long eventId,
                             HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return eventService.getById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventDto cansel(@PathVariable(name = "userId") Long userId,
                           @PathVariable(name = "eventId") Long eventId,
                           HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return eventService.cansel(userId, eventId);
    }


    @GetMapping("/nearby")
    public List<EventDto> findNearbyByAddress(@RequestParam(name = "city") Optional<String > city,
                                              @RequestParam(name = "street") Optional<String> street,
                                              @RequestParam(name = "number") Optional<String> number,
                                              @RequestParam(name = "lat") Optional<Float> lat,
                                              @RequestParam(name = "lon") Optional<Float> lon,
                                              @RequestParam(name = "distance", defaultValue = "1") float distance,
                                              HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());

        return ((city.isPresent()&&street.isPresent()&&number.isPresent()) ?
                eventService.findNearbyByAddress(city.get(), street.get(), number.get(), distance) :
                eventService.searchFindNearbyByCoordinate(lat, lon, distance));
    }



}
