package com.bravo.backend.controller;

import com.bravo.backend.DTO.NoteResponseDto;
import com.bravo.backend.DTO.TagRequestDto;
import com.bravo.backend.DTO.TagResponseDto;
import com.bravo.backend.models.Note;
import com.bravo.backend.models.Tag;
import com.bravo.backend.service.NoteService;
import com.bravo.backend.service.TagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagRequestDto request) {
        try {
            Tag tag = tagService.createTag(request.getName());
            return ResponseEntity.ok(toResponseDto(tag));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagResponseDto> response = tags.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id)
                .map(tag -> ResponseEntity.ok(toResponseDto(tag)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> updateTag(@PathVariable Long id, @Valid @RequestBody TagRequestDto request) {
        try {
            Tag updatedTag = tagService.updateTag(id, request.getName());
            return ResponseEntity.ok(toResponseDto(updatedTag));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        try {
            tagService.deleteTag(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all notes with a specific tag
    @GetMapping("/{id}/notes")
    public ResponseEntity<List<NoteResponseDto>> getNotesByTag(@PathVariable Long id) {
        try {
            List<Note> notes = noteService.getNotesByTag(id);
            List<NoteResponseDto> response = notes.stream()
                    .map(this::toNoteResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private TagResponseDto toResponseDto(Tag tag) {
        // Aquí podrías calcular el noteCount si tienes el método en el repository
        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .noteCount(0) // Implementar si necesitas el conteo
                .build();
    }

    private NoteResponseDto toNoteResponseDto(Note note) {
        Set<String> tagNames = note.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

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
