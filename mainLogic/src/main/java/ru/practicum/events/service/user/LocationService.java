package ru.practicum.events.service.user;

import org.springframework.stereotype.Service;
import ru.practicum.HTTPclient.Client;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;

@Service
public class LocationService {

  private final Client client;

    public LocationService( Client client) {

        this.client = client;
    }

    public LocationShort toCoordinate(String city, String street, String number){
        return client.addressToLocation(city,street,number);
    }

    public Address toAddress(float lon, float lat){
       return client.coordinateToAddress(lon,lat);
    }


}
