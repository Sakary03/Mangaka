package com.graduation.mangaka.controller;

import com.graduation.mangaka.model.UserFollow;
import com.graduation.mangaka.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class UserFollowController {
    @Autowired
    UserFollowService userFollowService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserFollow(@PathVariable Long userId,
                                           @RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(defaultValue = "10")  int limit) {
        return ResponseEntity.ok().body(userFollowService.getAllUserFollowManga(limit, offset, userId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> userNewFollow(@RequestParam Long userId,
                                           @RequestParam Long mangaId) {
        return ResponseEntity.ok().body(userFollowService.addUserNewFollow(userId, mangaId));
    }

    @GetMapping("/manga/{mangaId}")
    public ResponseEntity<?> getMangaFollowers(@PathVariable Long mangaId,
                                           @RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(defaultValue = "10")  int limit) {
        return ResponseEntity.ok().body(userFollowService.getMangaFollowers(limit, offset, mangaId));
    }

    @GetMapping("/manga/{mangaId}/count")
    public ResponseEntity<?> getMangaFollowersCount(@PathVariable Long mangaId) {
        return ResponseEntity.ok().body(userFollowService.getMangaFollowersCount(mangaId));
    }

    @GetMapping("/manga/{mangaId}/user/{userId}")
    public ResponseEntity<?> getMangaFollowersCount(@PathVariable Long mangaId,
                                                    @PathVariable Long userId) {
        return ResponseEntity.ok().body(userFollowService.checkDoesUserFollowed(mangaId, userId));
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<?> getUserFollowCount(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userFollowService.getAllUserFollowMangaCount(userId));
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<?> unfollowManga(@RequestParam Long userId,
                                           @RequestParam Long mangaId) {
        return ResponseEntity.ok().body(userFollowService.unfollowManga(mangaId, userId));
    }
}
