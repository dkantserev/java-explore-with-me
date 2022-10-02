package ru.practicum.events.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.model.State;
import ru.practicum.events.service.admin.AdminEventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/admin")
public class AdminEventController {

    final private AdminEventService adminEventService;

    public AdminEventController(AdminEventService adminEventService) {
        this.adminEventService = adminEventService;
    }

    @GetMapping("/events")
    public List<EventDto> getByParam(@RequestParam (name ="users",defaultValue = "") List<Long> users,
                                     @RequestParam (name ="states",defaultValue = "")List<State> states,
                                     @RequestParam(name="categories",defaultValue = "")List<Long> categories,
                                     @RequestParam(name= "rangeStart") Optional<String> rangeStart,
                                     @RequestParam(name ="rangeEnd")Optional<String> rangeEnd,
                                     @RequestParam(name ="from",defaultValue = "0") Long from,
                                     @RequestParam(name ="size",defaultValue = "10") Long size){

        return adminEventService.findByParam(users,states,categories,rangeStart,rangeEnd,from,size);
    }

}
