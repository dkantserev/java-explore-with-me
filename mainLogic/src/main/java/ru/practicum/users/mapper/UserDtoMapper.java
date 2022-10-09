package ru.practicum.users.mapper;


import ru.practicum.users.dto.UserDto;
import ru.practicum.users.model.User;


public class UserDtoMapper {

    public static UserDto toDTO(User use) {
        return UserDto.builder()
                .id(use.getId())
                .email(use.getEmail())
                .name(use.getName())
                .build();
    }

    public static User toModel(UserDto dto) {
        var user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setId(dto.getId());
        return user;
    }
}
