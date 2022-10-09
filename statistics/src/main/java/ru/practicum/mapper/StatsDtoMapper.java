package ru.practicum.mapper;


import ru.practicum.dto.StatsDto;
import ru.practicum.dto.StatsView;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatsDtoMapper {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT);

    public static Stats toModel(StatsDto dto) {

        var stats = new Stats();
        stats.setApp(dto.getApp());
        stats.setId(dto.getId());
        stats.setUri(dto.getUri());
        stats.setTimestamp(LocalDateTime.parse(dto.getTimestamp(), DATE_FORMAT));
        stats.setIp(dto.getIp());
        return stats;
    }

    public static StatsDto toDto(Stats stats) {

        return StatsDto.builder().id(stats.getId())
                .app(stats.getApp())
                .uri(stats.getUri())
                .ip(stats.getIp())
                .timestamp(stats.getTimestamp().format(DATE_FORMAT))
                .build();
    }

    public static StatsView toDtoView(Stats stats) {

        return StatsView.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .build();
    }
}
