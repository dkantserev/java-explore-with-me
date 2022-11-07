package ru.practicum.compilations.controller.guest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDtoReturn;
import ru.practicum.compilations.service.CompilationsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
public class CompilationGuestController {

    private final CompilationsService service;

    public CompilationGuestController(CompilationsService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompilationDtoReturn> get(@RequestParam(name = "pinned", defaultValue = "true") Boolean pinned,
                                          @RequestParam(name = "from", defaultValue = "0") Long from,
                                          @RequestParam(name = "size", defaultValue = "10") Long size,
                                          HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return service.getForGuest(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDtoReturn getById(@PathVariable(name = "compId") Long compId,
                                        HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return service.getById(compId);
    }
}
