package ru.practicum.geocoding.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;
import ru.practicum.geocoding.geoService.LocationService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(path = "/geo")
public class GeoController {

    private final LocationService locationService;

    public GeoController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/getlocation")
    public LocationShort getLocationByAddress(@RequestParam(name = "city") String city,
                                              @RequestParam(name = "street") String street,
                                              @RequestParam(name = "number") String number,
                                              HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return locationService.toCoordinate(city, street, number);
    }

    @GetMapping("/getaddress")
    public Address getAddress(@RequestParam(name = "lon") float lon,
                              @RequestParam(name = "lat") float lat,
                              HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return locationService.toAddress(lon, lat);
    }
}
