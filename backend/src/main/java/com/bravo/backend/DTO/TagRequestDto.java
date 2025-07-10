package com.bravo.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {
    @NotBlank(message = "Tag name cannot be blank")
    @Size(min = 1, max = 30, message = "Tag name must be between 1 and 30 characters")
    private String name;
}
