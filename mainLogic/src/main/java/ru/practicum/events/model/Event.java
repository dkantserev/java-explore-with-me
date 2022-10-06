package ru.practicum.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.request.model.Request;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String annotation;
    private Long category;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    @JsonIgnore
    private Location location;
    @Column(columnDefinition="TEXT")
    private String description;
    private LocalDateTime eventDate;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    @JsonIgnore
    private User user;
    private State state = State.UNDEFINED;
    @OneToMany(mappedBy = "eventM", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> requestList;
}
