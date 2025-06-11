package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.CommentRequestDTO;
import com.graduation.mangaka.model.Comment;
import com.graduation.mangaka.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(CommentRequestDTO request) {
        Comment comment = Comment.builder()
                .content(request.getContent())
                .userId(request.getUserId())
                .mangaId(request.getMangaId())
                .replyTo(request.getReplyTo())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByMangaId(Long mangaId) {
        return commentRepository.findByMangaId(mangaId);
    }

    public Optional<Comment> updateComment(Long id, String newContent) {
        return commentRepository.findById(id).map(comment -> {
            comment.setContent(newContent);
            comment.setUpdatedAt(LocalDateTime.now());
            return commentRepository.save(comment);
        });
    }

    public void deleteComment(Long id) {
        commentRepository.findById(id).ifPresent(comment -> {
            comment.setDeleted(true); // soft delete
            commentRepository.save(comment);
        });
    }
}
