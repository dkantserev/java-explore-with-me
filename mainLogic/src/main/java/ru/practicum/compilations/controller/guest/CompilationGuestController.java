package ru.practicum.compilations.controller.guest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDtoReturn;
import ru.practicum.compilations.service.CompilationsService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
public class CompilationGuestController {

    final private CompilationsService service;

    public CompilationGuestController(CompilationsService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompilationDtoReturn> get(@RequestParam(name = "pinned", defaultValue = "true") Boolean pinned,
                                          @RequestParam(name = "from", defaultValue = "0") Long from,
                                          @RequestParam(name = "size", defaultValue = "10") Long size) {
        return service.getForGuest(pinned, from, size);
    }
    @GetMapping("/{compId}")
    public CompilationDtoReturn getById(@PathVariable( name="compId") Long compId){
        return service.getById(compId);
    }
}