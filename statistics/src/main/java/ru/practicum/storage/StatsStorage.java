package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Stats;

@Repository
public interface StatsStorage extends JpaRepository<Stats,Long> {
}
