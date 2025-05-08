package com.graduation.mangaka.controller;

import com.graduation.mangaka.dto.request.CommentRequestDTO;
import com.graduation.mangaka.model.Comment;
import com.graduation.mangaka.service.CommentLikeService;
import com.graduation.mangaka.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService likeService;

    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody CommentRequestDTO request) {
        return ResponseEntity.ok(commentService.createComment(request));
    }

    @GetMapping("/manga/{mangaId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long mangaId) {
        return ResponseEntity.ok(commentService.getCommentsByMangaId(mangaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable Long id, @RequestBody String newContent) {
        return commentService.updateComment(id, newContent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> toggleLike(@PathVariable Long id, @RequestParam Long userId) {
        boolean liked = likeService.toggleLike(id, userId);
        return ResponseEntity.ok(liked ? "Liked" : "Unliked");
    }

    @GetMapping("/{id}/likes/count")
    public ResponseEntity<Long> countLikes(@PathVariable Long id) {
        return ResponseEntity.ok(likeService.countLikes(id));
    }
}
