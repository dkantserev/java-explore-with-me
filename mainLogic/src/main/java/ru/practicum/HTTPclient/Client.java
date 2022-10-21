package ru.practicum.HTTPclient;


import lombok.extern.slf4j.Slf4j;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class Client {

    protected final RestTemplate rest = new RestTemplate();


    protected <T> boolean post(String path, T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, getHeaders());
        ResponseEntity<Object> response = rest.exchange(path, HttpMethod.POST, requestEntity, Object.class);

        return response.getStatusCode().is2xxSuccessful();
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
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
