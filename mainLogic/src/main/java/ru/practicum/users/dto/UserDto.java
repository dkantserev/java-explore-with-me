package ru.practicum.users.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UserDto {


    private Long id;
    @NotEmpty
    private String name;
    @Email
    private String email;
}
