package com.graduation.mangaka.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchMangaDTO {
    String keyword;
    String title;
    String author;
    String genres;
}
