package com.graduation.mangaka.service;

import com.graduation.mangaka.model.CommentLike;
import com.graduation.mangaka.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository likeRepository;

    public boolean toggleLike(Long commentId, Long userId) {
        return likeRepository.findByCommentIdAndUserId(commentId, userId).map(existing -> {
            likeRepository.delete(existing);
            return false; // unliked
        }).orElseGet(() -> {
            CommentLike like = CommentLike.builder()
                    .commentId(commentId)
                    .userId(userId)
                    .likedAt(LocalDateTime.now())
                    .build();
            likeRepository.save(like);
            return true; // liked
        });
    }

    public Long countLikes(Long commentId) {
        return likeRepository.countByCommentId(commentId);
    }
}

