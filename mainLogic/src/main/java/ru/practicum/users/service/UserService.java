package ru.practicum.users.service;


import org.springframework.stereotype.Service;

import ru.practicum.users.dto.UserDto;
import ru.practicum.users.mapper.UserDtoMapper;
import ru.practicum.users.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    final private UserStorage userStorage;


    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public UserDto add(UserDto user) {
        return UserDtoMapper.toDTO(userStorage.save(UserDtoMapper.toModel(user)));
    }

    public List<UserDto> get(List<Long> ids, Long from, Long size) {
        List<UserDto> r = new ArrayList<>();
        userStorage.findByListId(ids).forEach(o -> r.add(UserDtoMapper.toDTO(o)));
        return r.stream().skip(from).limit(size).collect(Collectors.toList());
    }

    public void delete(Long userID) {
        userStorage.deleteById(userID);
    }
}
