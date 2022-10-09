package ru.practicum.HTTPclient;


import lombok.Data;


@Data
public class ModelStats {
    private String app;

    private String uri;

    private String ip;

    private String timestamp;
}
