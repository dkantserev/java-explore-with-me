package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompilationDto {

    private Long id;
    private String title;
    private List<Long> events;
    boolean pinned;
}
