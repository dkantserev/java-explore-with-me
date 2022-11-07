package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.events.model.Event;

import java.util.List;

@Data
@Builder
public class CompilationDtoReturn {

    private Long id;
    private String title;
    private List<Event> events;
    boolean pinned;
}
