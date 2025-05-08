package com.graduation.mangaka.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "manga_id", nullable = false)
    private Long mangaId;

    @Column(name = "reply_to")
    private Long replyTo; // null if it's a top-level comment

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "commentId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes;
}

