package ru.practicum.users.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserService;




@Slf4j
@RestController
@RequestMapping(path = "/admin/users")

public class UserController {

    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserDto add( @RequestBody UserDto user) {

        return userService.add(user);
    }


}
