package ru.practicum.compilations.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.CompilationDtoReturn;
import ru.practicum.compilations.service.CompilationsService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {

    private final CompilationsService service;

    public CompilationAdminController(CompilationsService service) {
        this.service = service;
    }

    @PostMapping
    public CompilationDtoReturn add(@RequestBody CompilationDto dto) {
        return service.add(dto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable(name = "compId") Long id,
                       HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        service.delete(id);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable(name = "compId") Long compId,
                            @PathVariable(name = "eventId") Long eventId,
                            HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        service.deleteEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void pinFalse(@PathVariable(name = "compId") Long compId,
                         HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        service.setPinFalse(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinTrue(@PathVariable(name = "compId") Long compId,
                        HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        service.setPinTrue(compId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable(name = "compId") Long compId,
                         @PathVariable(name = "eventId") Long eventId,
                         HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        service.addEvent(compId, eventId);
    }

}
