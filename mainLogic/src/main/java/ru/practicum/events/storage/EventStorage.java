package ru.practicum.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface EventStorage extends JpaRepository<Event, Long> {

    @Query("select e from Event e where  e.state=?1")
    public List<Event> undefinde(State state);

    @Query("select e from Event e where e.user.id=?1 and e.id>?2 order by e.id ")
    public List<Event> findByUserId(Long id, Long from);

    @Query("select e from Event e where e.user.id=?1 and e.id=?2  ")
    public Event findByUserIdAndEventId(Long id, Long eventId);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) and e.category in(?3)" +
            " and e.eventDate>=?4 and e.eventDate<=?5 ")
    public List<Event> findByAllParam(List<Long> users, List<State> states, List<Long> categories,
                                      LocalDateTime rangeStart, LocalDateTime rangeEnd);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) and e.category in(?3) and e.eventDate<=?4  ")
    public List<Event> findByAllParamMinusStart(List<Long> users, List<State> states, List<Long> categories,
                                                LocalDateTime parse);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) and e.category in(?3)   ")
    public List<Event> findByAllParamMinusStartMinusEnd(List<Long> users, List<State> states, List<Long> categories);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) ")
    public List<Event> findByAllParamMinusStartMinusEndMinusCategories(List<Long> users, List<State> states);

    @Query("select  e from  Event e where e.user.id in(?1) ")
    public List<Event> findByAllParamMinusStartMinusEndMinusCategoriesMinusStates(List<Long> users);

    @Query("select  e from  Event e where e.state in(?1) ")
    public List<Event> findByStates(List<State> states);

    @Query("select  e from  Event e where e.category in(?1) ")
    public List<Event> findByCategories(List<Long> categories);

    @Query("select  e from  Event e where e.eventDate<=?1 ")
    public List<Event> findByEnd(LocalDateTime rangeEnd);

    @Query("select  e from  Event e where e.eventDate>=?1 ")
    public List<Event> findByStart(LocalDateTime rangeStart);

    @Query("select  e from  Event e where e.eventDate>=?1 and e.eventDate<=?1 ")
    public List<Event>  findByStartAndEnd(LocalDateTime rangeStart, LocalDateTime rangeEnd);

    @Query("select  e from  Event e where e.state in(?1) and e.category in(?2)  ")
    public List<Event> findByStatesAndCategories(List<State> states, List<Long> categories);

    @Query("select  e from  Event e where e.user.id in(?1) and e.category in(?2)  ")
    public List<Event>findByUsersAndCategories(List<Long> users, List<Long> categories);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2)  ")
    public List<Event> findByUsersAndState(List<Long> users, List<State> states);
}
