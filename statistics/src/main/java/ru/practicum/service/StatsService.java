package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.StatsDto;
import ru.practicum.mapper.StatsDtoMapper;
import ru.practicum.storage.StatsStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatsService {

    private final StatsStorage storage;

    public StatsService(StatsStorage storage) {
        this.storage = storage;
    }

    public StatsDto add(StatsDto dto) {

        return StatsDtoMapper.toDto(storage.save(StatsDtoMapper.toModel(dto)));
    }

    public List<StatsDto> get() {
        List<StatsDto> r = new ArrayList<>();
        storage.findAll().forEach(o->r.add(StatsDtoMapper.toDto(o)));
        return r;
    }
}
