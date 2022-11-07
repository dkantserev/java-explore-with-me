package ru.practicum.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Location;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationStorage extends JpaRepository<Location, Long> {

    @Query("select l from Location l where l.lat=?1 and l.lon=?2 ")
    Optional<Location> findIdByCoordinates(float lat, float lon);

    @Query("select l from Location l where function('distance',l.lat,l.lon,?1,?2)<=?3  ")
    List<Location> searchLocationByFunctionDistance(float lat2, float lon2, float distance);

}
