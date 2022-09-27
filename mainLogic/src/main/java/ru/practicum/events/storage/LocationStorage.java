package ru.practicum.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Location;

@Repository
public interface LocationStorage extends JpaRepository<Location,Long> {
}
