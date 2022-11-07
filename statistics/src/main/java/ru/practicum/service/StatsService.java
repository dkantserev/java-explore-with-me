package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.StatsDto;
import ru.practicum.dto.StatsView;
import ru.practicum.mapper.StatsDtoMapper;
import ru.practicum.model.Stats;
import ru.practicum.storage.StatsStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatsService {

    private final StatsStorage storage;

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(FORMAT);

    public StatsService(StatsStorage storage) {
        this.storage = storage;
    }

    public StatsDto add(StatsDto dto) {

        return StatsDtoMapper.toDto(storage.save(StatsDtoMapper.toModel(dto)));
    }

    public List<StatsView> get(Optional<String> start, Optional<String> end, List<String> uris, Boolean unique) {

        List<Stats> r = new ArrayList<>();
        List<StatsView> r2 = new ArrayList<>();
        if (start.isPresent() && end.isPresent() && !uris.isEmpty() && unique) {
            r = storage.findByAllParam(LocalDateTime.parse(start.get(), DATE_FORMAT),
                    LocalDateTime.parse(end.get(), DATE_FORMAT), uris, true);
        }
        if (start.isPresent() && end.isPresent() && !uris.isEmpty() && !unique) {
            r = storage.findByAllParamMinusUnique(LocalDateTime.parse(start.get(), DATE_FORMAT),
                    LocalDateTime.parse(end.get(), DATE_FORMAT), uris);
        }
        if (start.isPresent() && end.isPresent() && uris.isEmpty() && !unique) {
            r = storage.findByAllParamMinusUniqueMinusUris(LocalDateTime.parse(start.get(), DATE_FORMAT),
                    LocalDateTime.parse(end.get(), DATE_FORMAT));
        }
        if (start.isPresent() && end.isEmpty() && uris.isEmpty() && !unique) {
            r = storage.findByStart(LocalDateTime.parse(start.get(), DATE_FORMAT));
        }
        if (start.isEmpty() && end.isPresent() && uris.isEmpty() && !unique) {
            r = storage.findByEnd(LocalDateTime.parse(end.get(), DATE_FORMAT));
        }
        for (Stats stats : r) {
            StatsView s = StatsDtoMapper.toDtoView(stats);
            s.setHits(storage.giveCount(stats.getUri()));
            r2.add(s);

        }
        return r2;
    }


    public Long getViews(Long eventId) {
        String uri = "/events/" + eventId.toString();
        return storage.giveCount(uri);
    }
}
