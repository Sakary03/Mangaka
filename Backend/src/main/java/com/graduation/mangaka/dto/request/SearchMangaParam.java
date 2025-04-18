package com.graduation.mangaka.dto.request;

import com.graduation.mangaka.model.TypeAndRole.Genres;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchMangaParam {
    private String keyword;
    private List<Genres> genres;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int offset;
    private int limit;
}
