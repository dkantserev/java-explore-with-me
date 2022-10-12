package ru.practicum.events.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;
import ru.practicum.events.model.UpdateEventRequest;
import ru.practicum.events.service.user.EventService;
import ru.practicum.events.service.user.LocationService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class EventController {

    private final EventService eventService;

    private final LocationService locationService;


    public EventController(EventService eventService, LocationService locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
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

    @GetMapping("/nearby/{lat}/{lon}/{distance}")
    public List<EventDto> findNearby(@PathVariable(name = "lat") float lat,
                                     @PathVariable(name = "lon") float lon,
                                     @PathVariable(name = "distance") float distance) {
        return eventService.searchFindNearbyByCoordinate(lat, lon, distance);
    }

    @GetMapping("/nearby")
    public List<EventDto> findNearbyByAddress(@RequestParam(name = "city") String city,
                                              @RequestParam(name = "street") String street,
                                              @RequestParam(name = "number") String number,
                                              @RequestParam(name = "distance") Float distance) {
        return eventService.findNearbyByAddress(city, street, number, distance);
    }


    @GetMapping("/getlocation")
    public LocationShort getLocationByAddress(@RequestParam(name = "city") String city,
                                              @RequestParam(name = "street") String street,
                                              @RequestParam(name = "number") String number) {
        return locationService.toCoordinate(city, street, number);
    }

    @GetMapping("/getaddress")
    public Address getAddress(@RequestParam(name = "lon") float lon,
                              @RequestParam(name = "lat") float lat) {
        return locationService.toAddress(lon, lat);
    }


}
