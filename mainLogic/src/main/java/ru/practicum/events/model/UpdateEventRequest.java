package ru.practicum.events.model;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
public class UpdateEventRequest {

    @Min(20)
    @Max(2000)
    private String annotation;
    private Long category;
    @Min(20)
    @Max(7000)
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    private Location location;
    private State state;
    @Min(3)
    @Max(120)
    private String title;




}
