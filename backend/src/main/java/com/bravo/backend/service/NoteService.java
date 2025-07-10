package com.bravo.backend.service;

import com.bravo.backend.models.Note;
import com.bravo.backend.models.Tag;
import com.bravo.backend.repository.NoteRepository;
import com.bravo.backend.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor // constructor-based dependency injection
public class NoteService {
    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;

    public Note createNote(Note note) {
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }


    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note updateNote(Long id, Note noteDetails) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setUpdatedAt(LocalDateTime.now());

        // Update tags if provided
        if (noteDetails.getTags() != null) {
            note.setTags(noteDetails.getTags());
        }

        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    public Note archiveNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));

        note.setArchived(true);
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note unarchiveNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));

        note.setArchived(false);
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public List<Note> getActiveNotes() {
        return noteRepository.findActiveNotesOrderByTitleIgnoreCase();
    }

    public List<Note> getArchivedNotes() {
        return noteRepository.findArchivedNotesOrderByTitleIgnoreCase();
    }

    public Note addTagToNoteByName(Long noteId, String tagName) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));

        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build()));

        // To avoid ConcurrentModificationException
        Set<Tag> tags = note.getTags() == null ? new HashSet<>() : new HashSet<>(note.getTags());
        tags.add(tag);
        note.setTags(tags);

        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }


    public Note removeTagFromNoteByName(Long noteId, String tagName) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));

        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new RuntimeException("Tag not found with name: " + tagName));

        note.getTags().remove(tag);
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public List<Note> getNotesByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));

        return noteRepository.findByTags_Name(tag.getName());
    }

    public List<Note> getNotesByTagName(String tagName) {
        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new RuntimeException("Tag not found with name: " + tagName));

        return noteRepository.findByTags_Name(tag.getName());
    }

}
