package ru.practicum.events.service;

import org.springframework.stereotype.Service;
import ru.practicum.categories.storage.CategoryStorage;
import ru.practicum.events.dto.EventDto;

import ru.practicum.events.dto.EventDtoGuest;
import ru.practicum.events.mapper.EventDtoMapper;
import ru.practicum.events.model.State;
import ru.practicum.events.storage.EventStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestEventService {

    private final EventStorage eventStorage;
    private final CategoryStorage categoryStorage;

    public GuestEventService(EventStorage eventStorage, CategoryStorage categoryStorage) {
        this.eventStorage = eventStorage;
        this.categoryStorage = categoryStorage;
    }

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public List<EventDtoGuest> get(Optional<String> text, List<Long> categories, Optional<Boolean> paid,
                              Optional<String> rangeStart, Optional<String> rangeEnd, Optional<Boolean> onlyAvailable,
                              Optional<String> sort, Long from, Long size) {

        List<EventDtoGuest> r = new ArrayList<>();
        if (text.isPresent() && !categories.isEmpty() && paid.isPresent() && rangeStart.isPresent()
                && rangeEnd.isPresent() && onlyAvailable.isPresent()) {
            eventStorage.findByAllParamPlusTextPlusAvailable(text.get().toLowerCase(), categories,
                            paid.get(), LocalDateTime.parse(rangeStart.get(), DateTimeFormatter.ofPattern(FORMAT)),
                            LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern(FORMAT)), onlyAvailable.get())
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isEmpty() && !categories.isEmpty() && paid.isPresent() && rangeStart.isPresent()
                && rangeEnd.isPresent() && onlyAvailable.isPresent()) {
            eventStorage.findByAllParamPlusAvailable(categories,
                            paid.get(), LocalDateTime.parse(rangeStart.get(), DateTimeFormatter.ofPattern(FORMAT)),
                            LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern(FORMAT)), onlyAvailable.get())
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isEmpty() && !categories.isEmpty() && paid.isPresent() && rangeStart.isPresent()
                && rangeEnd.isPresent() && onlyAvailable.isEmpty()) {
            eventStorage.findByAllParamGuest(categories,
                            paid.get(), LocalDateTime.parse(rangeStart.get(), DateTimeFormatter.ofPattern(FORMAT)),
                            LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern(FORMAT)))
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isEmpty() && !categories.isEmpty() && paid.isEmpty() && rangeStart.isPresent()
                && rangeEnd.isPresent() && onlyAvailable.isEmpty()) {
            eventStorage.findByAllParamGuestMinusPaid(categories,
                            LocalDateTime.parse(rangeStart.get(), DateTimeFormatter.ofPattern(FORMAT)),
                            LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern(FORMAT)))
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isEmpty() && categories.isEmpty() && paid.isEmpty() && rangeStart.isPresent()
                && rangeEnd.isPresent() && onlyAvailable.isEmpty()) {
            eventStorage.findByStartAndEnd(
                            LocalDateTime.parse(rangeStart.get(), DateTimeFormatter.ofPattern(FORMAT)),
                            LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern(FORMAT)))
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isEmpty() && !categories.isEmpty() && paid.isEmpty() && rangeStart.isEmpty()
                && rangeEnd.isEmpty() && onlyAvailable.isEmpty()) {
            eventStorage.findByCategories(categories)
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isEmpty() && categories.isEmpty() && paid.isEmpty() && rangeStart.isEmpty()
                && rangeEnd.isPresent() && onlyAvailable.isEmpty()) {
            eventStorage.findByEnd(LocalDateTime.parse(rangeEnd.get(), DateTimeFormatter.ofPattern(FORMAT)))
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isEmpty() && categories.isEmpty() && paid.isEmpty() && rangeStart.isPresent()
                && rangeEnd.isEmpty() && onlyAvailable.isEmpty()) {
            eventStorage.findByStart(LocalDateTime.parse(rangeStart.get(), DateTimeFormatter.ofPattern(FORMAT)))
                    .forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        if (text.isPresent() && categories.isEmpty() && paid.isEmpty() && rangeStart.isEmpty()
                && rangeEnd.isEmpty() && onlyAvailable.isEmpty()) {
            eventStorage.findByText(text.get().toLowerCase()).forEach(o -> r.add(EventDtoMapper.toDtoGuest(o)));
        }
        for (EventDtoGuest eventDtoGuest : r) {
            eventDtoGuest.setCategory(categoryStorage.findById(eventStorage.findById(eventDtoGuest.getId()).orElseThrow().getCategory()).orElseThrow());
        }
        if (sort.isPresent()) {
            switch (sort.get()) {
                case ("EVENT_DATE"):
                    return r.stream().sorted((Comparator.comparing(EventDtoGuest::getEventDate))).skip(from).limit(size)
                            .collect(Collectors.toList());
                case ("VIEWS"):
            }
            return r.stream().sorted((Comparator.comparing(EventDtoGuest::getEventDate))).skip(from).limit(size)
                    .collect(Collectors.toList());
        }

        return r.stream().skip(from).limit(size).collect(Collectors.toList());
    }


    public EventDtoGuest getById(Long id) {
        if(eventStorage.findById(id).orElseThrow().getState()==State.PUBLISHED) {
            var r = EventDtoMapper.toDtoGuest(eventStorage.findById(id).orElseThrow());
            r.setCategory(categoryStorage.findById(eventStorage.findById(id).orElseThrow().getId()).orElseThrow());
            return r;
        }
        throw new RuntimeException();
    }
}
