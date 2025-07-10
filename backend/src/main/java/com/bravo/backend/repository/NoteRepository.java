package com.bravo.backend.repository;

import com.bravo.backend.models.Note;
import com.bravo.backend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.archived = false ORDER BY LOWER(n.title) ASC")
    List<Note> findActiveNotesOrderByTitleIgnoreCase();

    @Query("SELECT n FROM Note n WHERE n.archived = true ORDER BY LOWER(n.title) ASC")
    List<Note> findArchivedNotesOrderByTitleIgnoreCase();

    List<Note> findByTags_Name(String tagName);

}
