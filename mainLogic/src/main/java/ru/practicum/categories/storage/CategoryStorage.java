package ru.practicum.categories.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.categories.model.Category;

@Repository
public interface CategoryStorage extends JpaRepository<Category,Long> {

}
