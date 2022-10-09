package ru.practicum.compilations.service;

import org.springframework.stereotype.Service;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationDtoReturn;
import ru.practicum.compilations.mapper.MapperCompilationDto;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.compilations.storage.CompilationStorage;
import ru.practicum.events.model.Event;
import ru.practicum.events.storage.EventStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompilationsService {

    private final CompilationStorage storage;
    private final EventStorage eventStorage;

    public CompilationsService(CompilationStorage storage, EventStorage eventStorage) {
        this.storage = storage;
        this.eventStorage = eventStorage;
    }

    public CompilationDtoReturn add(CompilationDto dto) {

        var compilation = new Compilation();
        List<Event> l = new ArrayList<>();

        dto.getEvents().forEach(o -> l.add(eventStorage.findById(o).orElseThrow(RuntimeException::new)));

        compilation.setEvents(l);
        compilation.setPinned(dto.isPinned());
        compilation.setTitle(dto.getTitle());
        Long id = storage.saveAndFlush(compilation).getId();
        l.forEach(o -> o.getCompilation().add(storage.findById(id).orElseThrow(RuntimeException::new)));
        eventStorage.flush();

        return MapperCompilationDto.toDtoReturn(storage.findById(id).orElseThrow(RuntimeException::new));


    }


    public void delete(Long id) {
        storage.deleteById(id);
    }

    public void deleteEvent(Long compId, Long eventId) {
        var e = storage.findById(compId).orElseThrow(RuntimeException::new);
        e.getEvents().removeIf(o -> Objects.equals(o.getId(), eventId));
        storage.save(e);

    }

    public void setPinFalse(Long compId) {
        storage.findById(compId).orElseThrow().setPinned(false);
    }

    public void setPinTrue(Long compId) {
        storage.findById(compId).orElseThrow().setPinned(true);
    }

    public void addEvent(Long compId, Long eventId) {
        var e = storage.findById(compId).orElseThrow(RuntimeException::new);
        e.getEvents().add(eventStorage.findById(eventId).orElseThrow());
        storage.save(e);
    }

    public List<CompilationDtoReturn> getForGuest(Boolean pinned, Long from, Long size) {
        List<CompilationDtoReturn> r = new ArrayList<>();
        storage.findByPinned(pinned).forEach(o -> r.add(MapperCompilationDto.toDtoReturn(o)));
        return r.stream().skip(from).limit(size).collect(Collectors.toList());
    }

    public CompilationDtoReturn getById(Long compId) {
        return MapperCompilationDto.toDtoReturn(storage.findById(compId).orElseThrow());
    }
}
