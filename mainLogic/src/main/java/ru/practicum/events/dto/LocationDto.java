package ru.practicum.events.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {

    private Long id;

    private Float lat;

    private Float lon;
}
