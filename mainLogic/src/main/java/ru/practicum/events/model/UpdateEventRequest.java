package ru.practicum.events.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;


@Data
public class UpdateEventRequest {
    @Min(20)
    @Max(2000)
    private String annotation;
    private Long category;
    @Min(20)
    @Max(7000)
    private String description;
    private LocalDateTime eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    @Min(3)
    @Max(120)
    private String title;




}
