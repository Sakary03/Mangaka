package com.graduation.mangaka.dto.request;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private String content;
    private Long userId;
    private Long mangaId;
    private Long replyTo; // Nullable
}
