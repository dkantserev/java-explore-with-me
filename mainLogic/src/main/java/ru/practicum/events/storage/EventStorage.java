package ru.practicum.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;

@Repository
public interface EventStorage extends JpaRepository<Event,Long> {
}
