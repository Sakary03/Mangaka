package com.graduation.mangaka.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchMangaDTO {
    private String title;
    private String author;
    private List<String> genres;
}

