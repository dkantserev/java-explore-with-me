package ru.practicum.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    private Long category;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn
    @JsonIgnore
    private Location location;
    private String description;
    private LocalDateTime eventDate;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn
    @JsonIgnore
    private User user;
    private State state = State.PUBLISHED;
}
