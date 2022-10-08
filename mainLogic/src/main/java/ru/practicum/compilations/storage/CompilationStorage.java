package ru.practicum.compilations.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.compilations.model.Compilation;

import java.util.List;

@Repository
public interface CompilationStorage extends JpaRepository<Compilation,Long> {

@Query("select c from Compilation c where c.pinned=?1")
    public List<Compilation> findByPinned(Boolean pinned);
}
