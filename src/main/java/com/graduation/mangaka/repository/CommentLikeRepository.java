package com.graduation.mangaka.repository;

import com.graduation.mangaka.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
    void deleteByCommentIdAndUserId(Long commentId, Long userId);
    Long countByCommentId(Long commentId);
}

