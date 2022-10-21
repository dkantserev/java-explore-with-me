package ru.practicum.HTTPclient;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
public class StatsClient extends Client{

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT);

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
}
