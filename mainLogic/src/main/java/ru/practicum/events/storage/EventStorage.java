package ru.practicum.events.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface EventStorage extends JpaRepository<Event, Long> {

    @Query("select e from Event e where  e.state=?1")
    List<Event> undefinde(State state);

    @Query("select e from Event e where e.user.id=?1 and e.id>?2 order by e.id ")
    List<Event> findByUserId(Long id, Long from);

    @Query("select e from Event e where e.user.id=?1 and e.id=?2  ")
    Event findByUserIdAndEventId(Long id, Long eventId);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) and e.category in(?3)" +
            " and e.eventDate>=?4 and e.eventDate<=?5 ")
    List<Event> findByAllParam(List<Long> users, List<State> states, List<Long> categories,
                               LocalDateTime rangeStart, LocalDateTime rangeEnd);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) and e.category in(?3) " +
            "and e.eventDate<=?4  ")
    List<Event> findByAllParamMinusStart(List<Long> users, List<State> states, List<Long> categories,
                                         LocalDateTime parse);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) and e.category in(?3)   ")
    List<Event> findByAllParamMinusStartMinusEnd(List<Long> users, List<State> states, List<Long> categories);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2) ")
    List<Event> findByAllParamMinusStartMinusEndMinusCategories(List<Long> users, List<State> states);

    @Query("select  e from  Event e where e.user.id in(?1) ")
    List<Event> findByAllParamMinusStartMinusEndMinusCategoriesMinusStates(List<Long> users);

    @Query("select  e from  Event e where e.state in(?1) ")
    List<Event> findByStates(List<State> states);

    @Query("select  e from  Event e where e.category in(?1) ")
    List<Event> findByCategories(List<Long> categories);

    @Query("select  e from  Event e where e.eventDate<=?1 ")
    List<Event> findByEnd(LocalDateTime rangeEnd);

    @Query("select  e from  Event e where e.eventDate>=?1 ")
    List<Event> findByStart(LocalDateTime rangeStart);

    @Query("select  e from  Event e where e.eventDate>=?1 and e.eventDate<=?1 ")
    List<Event> findByStartAndEnd(LocalDateTime rangeStart, LocalDateTime rangeEnd);

    @Query("select  e from  Event e where e.state in(?1) and e.category in(?2)  ")
    List<Event> findByStatesAndCategories(List<State> states, List<Long> categories);

    @Query("select  e from  Event e where e.user.id in(?1) and e.category in(?2)  ")
    List<Event> findByUsersAndCategories(List<Long> users, List<Long> categories);

    @Query("select  e from  Event e where e.user.id in(?1) and e.state in(?2)  ")
    List<Event> findByUsersAndState(List<Long> users, List<State> states);

    @Query("select e from Event e where lower( e.description) like concat('%',?1,'%')   or lower (e.annotation)" +
            " like concat('%',?1,'%') and e.category in(?2) and e.paid=?3   and e.eventDate>=?4 and e.eventDate<=?5 " +
            "and e.available=?6")
    List<Event> findByAllParamPlusTextPlusAvailable(String text, List<Long> categories, Boolean paid,
                                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                    Boolean onlyAvailable);

    @Query("select e from Event e where lower( e.description) like concat('%',?1,'%')   or lower (e.annotation)" +
            " like concat('%',?1,'%')")
    List<Event> findByText(String text);

    @Query("select e from Event e where  e.category in(?1) and e.paid=?2   and e.eventDate>=?3 and e.eventDate<=?4 " +
            "and e.available=?5")
    List<Event> findByAllParamPlusAvailable(List<Long> categories, Boolean aBoolean, LocalDateTime parse,
                                            LocalDateTime parse1, Boolean aBoolean1);

    @Query("select e from Event e where  e.category in(?1) and e.paid=?2   and e.eventDate>=?3 and e.eventDate<=?4 ")
    List<Event> findByAllParamGuest(List<Long> categories, Boolean aBoolean, LocalDateTime parse, LocalDateTime parse1);

    @Query("select e from Event e where  e.category in(?1)  and e.eventDate>=?2 and e.eventDate<=?3 ")
    List<Event> findByAllParamGuestMinusPaid(List<Long> categories, LocalDateTime parse, LocalDateTime parse1);

    @Query("select e from Event  e where e.location in(?1)")
    List<Event> findByLocation(List<Location> l);
}
