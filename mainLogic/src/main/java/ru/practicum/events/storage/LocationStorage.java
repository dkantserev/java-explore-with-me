package ru.practicum.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Location;

import java.util.Optional;

@Repository
public interface LocationStorage extends JpaRepository<Location, Long> {

    @Query("select l from Location l where l.lat=?1 and l.lon=?2 ")
    public Optional<Location> findIdByCoordinates(float lat, float lon);

}
