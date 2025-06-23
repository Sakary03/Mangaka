package com.graduation.mangaka.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReadingHistoryResponseDTO {
    private Long id;
    private UserDTO user;
    private MangaDTO manga;
    private ChapterDTO chapter;
    private Timestamp createdAt;
}
