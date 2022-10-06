package ru.practicum.events.dto;


import lombok.Builder;
import lombok.Data;
import ru.practicum.events.model.State;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
@Builder
public class EventDto {

    private Long id;
    @Min(20)
    @Max(2000)
    private String annotation;
    private Long category;
    private LocationDto location;
    @Min(20)
    @Max(7000)
    private String description;
    private String eventDate;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private State state;
    @Min(3)
    @Max(120)
    private String title;

}
