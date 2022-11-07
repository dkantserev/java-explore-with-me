package ru.practicum.HTTPclient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.errorApi.exception.LocationNotFoundException;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class GeoClient extends Client {

    private final String path = "https://api.geotree.ru/address.php?";
    private final String key = "A2tnnft2vxQj";

    public Address coordinateToAddress(float lon, float lat) {

        Address address = new Address();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(path)
                .queryParam("key", "{key}")
                .queryParam("lon", "{lon}")
                .queryParam("lat", "{lat}")
                .encode().toUriString();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("lon", Float.toString(lon));
        queryParam.put("lat", Float.toString(lat));
        queryParam.put("key", key);
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders());
        ResponseEntity<String> parse = rest.exchange(
                urlTemplate, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                }, queryParam);
        JsonArray asJsonArray = JsonParser.parseString(Objects.requireNonNull(parse.getBody())).getAsJsonArray();
        JsonObject asJsonObject = asJsonArray.get(0).getAsJsonObject();
        String s = asJsonObject.getAsJsonPrimitive("value").getAsString();
        address.setAddress(s);
        return address;
    }

    public LocationShort addressToLocation(String city, String street, String number) {

        if (city.equalsIgnoreCase("санкт-петербург")) {
            city = "петербург";
        }
        String term = city + " " + street + " " + number + " ";
        LocationShort location;
        Gson g = new Gson();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(path)
                .queryParam("key", "{key}")
                .queryParam("term", "{term}")
                .encode().toUriString();
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders());

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("term", term);
        queryParam.put("key", key);
        ResponseEntity<String> entity = rest.exchange(
                urlTemplate, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                }, queryParam);
        if (Objects.requireNonNull(entity.getBody()).isEmpty()) {
            throw new LocationNotFoundException("bad address");
        }
        JsonArray asJsonArray = JsonParser.parseString(Objects.requireNonNull(entity.getBody())).getAsJsonArray();
        JsonObject asJsonObject = asJsonArray.get(0).getAsJsonObject();
        location = g.fromJson(asJsonObject.get("geo_center"), LocationShort.class);
        return location;
    }
}
