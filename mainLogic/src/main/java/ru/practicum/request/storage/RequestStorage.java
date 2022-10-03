package ru.practicum.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.Request;

@Repository
public interface RequestStorage extends JpaRepository<Request,Long> {
}
