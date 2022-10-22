package ru.practicum.geocoding.geoService;

import org.springframework.stereotype.Service;

import ru.practicum.HTTPclient.GeoClient;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;

@Service
public class LocationService {

    private final GeoClient httpClient;

    public LocationService(GeoClient client) {

        this.httpClient = client;
    }

    public LocationShort toCoordinate(String city, String street, String number) {
        return httpClient.addressToLocation(city, street, number);
    }

    public Address toAddress(float lon, float lat) {
        return httpClient.coordinateToAddress(lon, lat);
    }


}
