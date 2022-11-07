package ru.practicum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.HTTPclient.Client;
import ru.practicum.categories.dto.CategoryDto;

import ru.practicum.categories.service.CategoryService;
import ru.practicum.categories.storage.CategoryStorage;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.dto.LocationDto;

import ru.practicum.events.model.State;
import ru.practicum.events.service.user.EventService;
import ru.practicum.events.storage.EventStorage;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserService;
import ru.practicum.users.storage.UserStorage;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MainAppTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    Client client;
    @Autowired
    UserStorage userStorage;
    @Autowired
    CategoryStorage categoryStorage;
    @Autowired
    EventStorage eventStorage;

    UserDto user = UserDto.builder().name("user").email("email@mail.ru").build();
    CategoryDto categoryDto = CategoryDto.builder().id(1L).name("cinema").build();

    @Test
    public void complex() {
        assertEquals(userStorage.findById(userService.add(user).getId()).get().getEmail(), user.getEmail());
        assertEquals(categoryStorage.findById(categoryService.add(categoryDto).getId()).get().getName(),
                categoryDto.getName());
        LocationDto location = LocationDto.builder().lat(59.83059f).lon(30.37745f).build();
        EventDto event = EventDto.builder()
                .annotation("Премьера фильма.")
                .eventDate("2022-12-29 18:30:00")
                .category(1L)
                .description("Действительно самый лучший фильм, а не так как раньше.")
                .paid(true)
                .state(State.PUBLISHED)
                .title("Кино")
                .requestModeration(true)
                .location(location)
                .build();

        assertEquals(eventStorage.findById(eventService.add(1L, event).getId()).get().getAnnotation(),
                event.getAnnotation());
        assertEquals(eventService.findNearbyByAddress("Санкт-петербург","бухарестская","134",
                4f).get(0).getAnnotation(),event.getAnnotation());
        assertEquals(eventService.findNearbyByAddress("Санкт-петербург","бухарестская","134",
                2f).size(),0);
    }
}
