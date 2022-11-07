package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserShortDto {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;

}
