package ru.practicum.users.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/admin/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserDto add(@RequestBody UserDto user,
                       HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString());
        return userService.add(user);
    }

    @GetMapping
    public List<UserDto> getById(@RequestParam(name = "ids") List<Long> ids,
                                 @RequestParam(name = "from", defaultValue = "0") Long from,
                                 @RequestParam(name = "size", defaultValue = "10") Long size,
                                 HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return userService.get(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable(name = "userId") Long userID,
                           HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        userService.delete(userID);
    }


}
