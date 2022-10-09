package ru.practicum.HTTPclient;



import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void postStats(String url, String ip){
        String path = "http://stats-server:9090/hit";
        ModelStats m = new ModelStats();
        m.setApp("MainLogic");
        m.setUri(url);
        m.setIp(ip);
        m.setTimestamp(LocalDateTime.now().format(DATE_FORMAT));
        post(path,m);
    }

    public Long giveViews(Long eventId){
        String path = "http://stats-server:9090/views/"+eventId.toString();
        return getView(path,new HashMap<>());
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
