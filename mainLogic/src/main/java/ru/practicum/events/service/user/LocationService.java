package ru.practicum.events.service.user;

import org.springframework.stereotype.Service;
import ru.practicum.HTTPclient.Client;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;

@Service
public class LocationService {

  private final Client HttpClient;

    public LocationService( Client client) {

        this.HttpClient = client;
    }

    public LocationShort toCoordinate(String city, String street, String number){
        return HttpClient.addressToLocation(city,street,number);
    }

    public Address toAddress(float lon, float lat){
       return HttpClient.coordinateToAddress(lon,lat);
    }


}
