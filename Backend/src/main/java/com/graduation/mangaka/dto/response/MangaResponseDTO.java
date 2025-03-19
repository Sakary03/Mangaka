package com.graduation.mangaka.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MangaResponseDTO {
    private int id;
    private String title;
    private String author;
    private String description;
    private String overview;
    private String posterUrl;
    private String backgroundUrl;
}
