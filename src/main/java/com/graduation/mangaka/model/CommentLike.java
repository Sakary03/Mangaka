package com.graduation.mangaka.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"comment_id", "user_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "liked_at", nullable = false)
    private LocalDateTime likedAt;
}
