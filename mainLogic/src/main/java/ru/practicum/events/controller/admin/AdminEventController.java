package ru.practicum.events.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.model.State;
import ru.practicum.events.service.admin.AdminEventService;


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

    @PutMapping("/events/{eventId}")
    public EventDto edit(@RequestBody EventDto eventDto,
                         @PathVariable(name = "eventId") Long eventId){
        return adminEventService.edit(eventDto,eventId);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventDto publish(@PathVariable(name = "eventId") Long eventId){
        return adminEventService.publish(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventDto reject(@PathVariable(name = "eventId") Long eventId){
        return adminEventService.reject(eventId);
    }

    @PatchMapping("/events/undefined/publish")
    public List<EventDto> undefined (){
        return adminEventService.undefined();

    }



}
