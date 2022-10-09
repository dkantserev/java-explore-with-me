package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsStorage extends JpaRepository<Stats,Long> {

    @Query("select DISTINCT s from Stats s where s.uri  like (?3) and s.timestamp>=?1 and s.timestamp<=?2 ")
    List<Stats> findByAllParam(LocalDateTime parse, LocalDateTime parse1, List<String> uris, boolean b);

    @Query("select  s from Stats s where s.uri like(?3) and s.timestamp>=?1 and s.timestamp<=?2 ")
    List<Stats> findByAllParamMinusUnique(LocalDateTime parse, LocalDateTime parse1, List<String> uris);

    @Query("select  s from Stats s where  s.timestamp>=?1 and s.timestamp<=?2 ")
    List<Stats> findByAllParamMinusUniqueMinusUris(LocalDateTime parse, LocalDateTime parse1);

    @Query("select  s from Stats s where  s.timestamp>=?1  ")
    List<Stats> findByStart(LocalDateTime parse);

    @Query("select  s from Stats s where  s.timestamp<=?1  ")
    List<Stats> findByEnd(LocalDateTime parse);

    @Query("select Count(s.uri) from Stats s where s.uri=?1  ")
    Long giveCount(String uri);
}
