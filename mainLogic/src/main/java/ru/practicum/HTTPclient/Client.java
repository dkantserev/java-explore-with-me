package ru.practicum.HTTPclient;


import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.errorApi.exception.LocationNotFoundException;
import ru.practicum.events.dto.LocationShort;
import ru.practicum.events.model.Address;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class Client {

    protected final RestTemplate rest = new RestTemplate();

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT);


    protected <T> boolean post(String path, T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, getHeaders());
        ResponseEntity<Object> response = rest.exchange(path, HttpMethod.POST, requestEntity, Object.class);

        return response.getStatusCode().is2xxSuccessful();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    public void postStats(String url, String ip) {
        String path = "http://stats-server:9090/hit";
        ModelStats m = new ModelStats();
        m.setApp("MainLogic");
        m.setUri(url);
        m.setIp(ip);
        m.setTimestamp(LocalDateTime.now().format(DATE_FORMAT));
        post(path, m);
    }

    public Long giveViews(Long eventId) {
        String path = "http://stats-server:9090/views/" + eventId.toString();
        return getView(path, new HashMap<>());
    }

    public Address coordinateToAddress(float lon, float lat) {
        String path = "https://api.geotree.ru/address.php?";
        String key = "A2tnnft2vxQj";
        Address address = new Address();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(path)
                .queryParam("key", "{key}")
                .queryParam("lon", "{lon}")
                .queryParam("lat", "{lat}")
                .encode().toUriString();
        Map<String, String> QueryParam = new HashMap<>();
        QueryParam.put("lon", Float.toString(lon));
        QueryParam.put("lat", Float.toString(lat));
        QueryParam.put("key", key);
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders());
        ResponseEntity<String> parse = rest.exchange(
                urlTemplate, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                }, QueryParam);
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

        String path = "https://api.geotree.ru/address.php?";
        String key = "A2tnnft2vxQj";
        String term = city + " " + street + " " + number + " ";
        LocationShort location;
        Gson g = new Gson();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(path)
                .queryParam("key", "{key}")
                .queryParam("term", "{term}")
                .encode().toUriString();
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders());

        Map<String, String> QueryParam = new HashMap<>();
        QueryParam.put("term", term);
        QueryParam.put("key", key);
        ResponseEntity<String> entity = rest.exchange(
                urlTemplate, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                }, QueryParam);
        if (Objects.requireNonNull(entity.getBody()).isEmpty()) {
            throw new LocationNotFoundException("bad address");
        }
        JsonArray asJsonArray = JsonParser.parseString(Objects.requireNonNull(entity.getBody())).getAsJsonArray();
        JsonObject asJsonObject = asJsonArray.get(0).getAsJsonObject();
        location = g.fromJson(asJsonObject.get("geo_center"), LocationShort.class);
        return location;
    }


    protected List<ModelStats> get(String path, Map<String, Object> parameters) {
        HttpEntity<List<ModelStats>> requestEntity = new HttpEntity<>(getHeaders());

        ResponseEntity<List<ModelStats>> response =
                rest.exchange(
                        path, HttpMethod.GET, requestEntity,
                        new ParameterizedTypeReference<>() {
                        }, parameters);
        return response.getBody();
    }

    protected Long getView(String path, Map<String, Object> parameters) {
        HttpEntity<Long> requestEntity = new HttpEntity<>(getHeaders());
        ResponseEntity<Long> response =
                rest.exchange(
                        path, HttpMethod.GET, requestEntity,
                        new ParameterizedTypeReference<>() {
                        }, parameters);
        return response.getBody();
    }


}
