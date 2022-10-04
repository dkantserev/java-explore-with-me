package ru.practicum.users.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.users.model.User;

import java.util.List;

@Repository
public interface UserStorage extends JpaRepository<User, Long> {

    @Query("select u from User  u where u.id in (?1)")
    public List<User> findByListId(List<Long> ids);
}
