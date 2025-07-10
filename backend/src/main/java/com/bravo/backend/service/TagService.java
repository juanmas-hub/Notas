package com.bravo.backend.service;

import com.bravo.backend.models.Tag;
import com.bravo.backend.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag createTag(String tagName) {
        Optional<Tag> existingTag = tagRepository.findByName(tagName);
        if (existingTag.isPresent()) {
            throw new RuntimeException("Tag already exists: " + tagName);
        }

        Tag tag = Tag.builder()
                .name(tagName)
                .build();

        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag updateTag(Long id, String newName) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));

        Optional<Tag> existingTag = tagRepository.findByName(newName);
        if (existingTag.isPresent() && !existingTag.get().getId().equals(id)) {
            throw new RuntimeException("Tag with name '" + newName + "' already exists");
        }

        tag.setName(newName);
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }

    public List<Tag> getUnusedTags() {
        return tagRepository.findTagsNotUsedByAnyNote();
    }

}
