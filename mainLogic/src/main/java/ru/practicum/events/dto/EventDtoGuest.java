package ru.practicum.events.dto;


import lombok.Builder;
import lombok.Data;
import ru.practicum.categories.model.Category;
import ru.practicum.events.model.State;
import ru.practicum.users.model.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
@Builder
public class EventDtoGuest {

    private Long id;
    @Min(20)
    @Max(2000)
    private String annotation;
    private Category category;
    private LocationDto location;
    private User initiator;
    @Min(20)
    @Max(7000)
    private String description;
    private String eventDate;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private State state;
    private Long views;
    @Min(3)
    @Max(120)
    private String title;
    Long confirmedRequests = 1L;

}
