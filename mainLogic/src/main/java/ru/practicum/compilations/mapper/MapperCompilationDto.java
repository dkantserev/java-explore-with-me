package ru.practicum.compilations.mapper;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationDtoReturn;
import ru.practicum.compilations.model.Compilation;

import java.util.ArrayList;
import java.util.List;

public class MapperCompilationDto {

    public static CompilationDto toDto(Compilation compilation) {
        List<Long> l = new ArrayList<>();
        compilation.getEvents().forEach(o -> l.add(o.getId()));
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .events(l)
                .title(compilation.getTitle())
                .build();
    }

    public static CompilationDtoReturn toDtoReturn(Compilation compilation) {

        return CompilationDtoReturn.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents())
                .title(compilation.getTitle())
                .build();
    }
}
