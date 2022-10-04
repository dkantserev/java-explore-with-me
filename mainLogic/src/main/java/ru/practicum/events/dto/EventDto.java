package ru.practicum.events.dto;


import lombok.Builder;
import lombok.Data;
import ru.practicum.events.model.Location;
import ru.practicum.users.model.User;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
@Builder
public class EventDto {

    private Long eventId;
    @Min(20)
    @Max(2000)
    private String annotation;
    private Long category;
    private Location location;
    @Min(20)
    @Max(7000)
    private String description;
    private String eventDate;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    @Min(3)
    @Max(120)
    private String title;

}
