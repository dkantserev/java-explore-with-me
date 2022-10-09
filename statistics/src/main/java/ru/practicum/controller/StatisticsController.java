package ru.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatsDto;
import ru.practicum.dto.StatsView;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController

public class StatisticsController {

    private final StatsService service;

    public StatisticsController(StatsService service) {
        this.service = service;
    }

    @PostMapping(path = "/hit")
    public StatsDto add(@RequestBody StatsDto statsDto) {
        return service.add(statsDto);
    }

    @GetMapping("/views/{eventId}")
    public Long giveViews(@PathVariable (name = "eventId") Long eventId){
        return service.getViews(eventId);
    }

    @GetMapping( path = "stats")
    public List<StatsView> get(@RequestParam (name ="start") Optional<String> start,
                               @RequestParam(name= "end") Optional<String> end,
                               @RequestParam(name ="uris",defaultValue = "") List<String> uris,
                               @RequestParam(name ="unique",defaultValue = "false") Boolean unique  ){
        return service.get(start,end,uris,unique);
    }


}


