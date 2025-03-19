package com.graduation.mangaka.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class MangaRequestDTO {
    @NotBlank(message = "Title is required")
    String title;
    @NotBlank(message = "Author is required")
    String author;
    @NotBlank(message = "Description is required")
    String description;
    @NotBlank(message = "Overview is required")
    String overview;

}
