package com.bravo.backend.controller;

import com.bravo.backend.DTO.NoteRequestDto;
import com.bravo.backend.DTO.NoteResponseDto;
import com.bravo.backend.models.Note;
import com.bravo.backend.models.Tag;
import com.bravo.backend.service.NoteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteRequestDto request) {
        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Note savedNote = noteService.createNote(note);

        if (request.getTagNames() != null && !request.getTagNames().isEmpty()) {
            for (String tagName : request.getTagNames()) {
                savedNote = noteService.addTagToNoteByName(savedNote.getId(), tagName);
            }
        }

        return ResponseEntity.ok(toResponseDto(savedNote));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id)
                .map(note -> ResponseEntity.ok(toResponseDto(note)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequestDto request) {
        try {
            Note noteDetails = Note.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .build();

            Note updatedNote = noteService.updateNote(id, noteDetails);

            if (request.getTagNames() != null) {
                for (String tagName : request.getTagNames()) {
                    updatedNote = noteService.addTagToNoteByName(updatedNote.getId(), tagName);
                }
            }

            return ResponseEntity.ok(toResponseDto(updatedNote));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<NoteResponseDto>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        List<NoteResponseDto> response = notes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NoteResponseDto>> getActiveNotes() {
        List<Note> notes = noteService.getActiveNotes();
        List<NoteResponseDto> response = notes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<NoteResponseDto>> getArchivedNotes() {
        List<Note> notes = noteService.getArchivedNotes();
        List<NoteResponseDto> response = notes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<NoteResponseDto> archiveNote(@PathVariable Long id) {
        try {
            Note archivedNote = noteService.archiveNote(id);
            return ResponseEntity.ok(toResponseDto(archivedNote));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/unarchive")
    public ResponseEntity<NoteResponseDto> unarchiveNote(@PathVariable Long id) {
        try {
            Note unarchivedNote = noteService.unarchiveNote(id);
            return ResponseEntity.ok(toResponseDto(unarchivedNote));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<NoteResponseDto> addTagToNote(@PathVariable Long id, @RequestParam String tagName) {
        try {
            Note updatedNote = noteService.addTagToNoteByName(id, tagName);
            return ResponseEntity.ok(toResponseDto(updatedNote));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/tags")
    public ResponseEntity<NoteResponseDto> removeTagFromNote(@PathVariable Long id, @RequestParam String tagName) {
        try {
            Note updatedNote = noteService.removeTagFromNoteByName(id, tagName);
            return ResponseEntity.ok(toResponseDto(updatedNote));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/by-tag")
    public ResponseEntity<List<NoteResponseDto>> getNotesByTag(@RequestParam String tagName) {
        try {
            List<Note> notes = noteService.getNotesByTagName(tagName);
            List<NoteResponseDto> response = notes.stream()
                    .map(this::toResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    private NoteResponseDto toResponseDto(Note note) {
        Set<String> tagNames = note.getTags() != null ?
                note.getTags().stream()
                        .filter(Objects::nonNull)
                        .map(Tag::getName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()) :
                Collections.emptySet();

        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .archived(note.isArchived())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .tags(tagNames)
                .build();
    }
}
