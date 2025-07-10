package com.bravo.backend.repository;

import com.bravo.backend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("SELECT t FROM Tag t WHERE t NOT IN (SELECT DISTINCT tag FROM Note n JOIN n.tags tag)")
    List<Tag> findTagsNotUsedByAnyNote();

}
