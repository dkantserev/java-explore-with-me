package ru.practicum.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.Request;

import java.util.List;

@Repository
public interface RequestStorage extends JpaRepository<Request,Long> {

    @Query("select r from Request r where r.requester=?1")
    public List<Request> findByRequesterId(Long requester);

    @Query("select r from Request  r where  r.eventM.user.id=?1 and r.event=?2")
    public List<Request> findByRequesterIdAndEvent(Long eventId,Long userId);
}
