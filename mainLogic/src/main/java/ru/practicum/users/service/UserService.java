package ru.practicum.users.service;


import org.springframework.stereotype.Service;

import ru.practicum.users.dto.UserDto;
import ru.practicum.users.mapper.UserDtoMapper;
import ru.practicum.users.storage.UserStorage;

@Service
public class UserService {
    final private UserStorage userStorage;


    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public UserDto add(UserDto user) {
        return UserDtoMapper.toDTO(userStorage.save(UserDtoMapper.toModel(user)));
    }
}
