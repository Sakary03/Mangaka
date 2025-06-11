package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.response.UserFollowResponseDTO;
import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.User;
import com.graduation.mangaka.model.UserFollow;
import com.graduation.mangaka.repository.MangaRepository;
import com.graduation.mangaka.repository.UserFollowRepository;
import com.graduation.mangaka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserFollowService {
    @Autowired
    UserFollowRepository userFollowRepository;
    @Autowired
    private MangaRepository mangaRepository;
    @Autowired
    private UserRepository userRepository;

    public Page<Manga> getAllUserFollowManga(int limt, int offset, Long userId) {
        Pageable pageable = PageRequest.of(offset,limt);
        return userFollowRepository.findMangaByUserId(userId, pageable);
    }

    public UserFollow addUserNewFollow(Long userId, Long mangaId) {
        UserFollowResponseDTO oldUserFollow = userFollowRepository.findUserFollowByUserIdAndMangaId(mangaId, userId);
        if (oldUserFollow!=null) return null;
        UserFollow userFollow = new UserFollow();
        User user=userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Manga manga=mangaRepository.findById(mangaId).orElseThrow(() -> new RuntimeException("Manga not found"));;
        userFollow.setManga(manga);
        userFollow.setUser(user);
        return userFollowRepository.save(userFollow);
    }

    public Page<User> getMangaFollowers(int limt, int offset, Long mangaId) {
        Pageable pageable = PageRequest.of(offset,limt);
        return userFollowRepository.findUserFollowManga(mangaId, pageable);
    }

    public int getMangaFollowersCount(Long mangaId) {
        return userFollowRepository.findUserFollowMangaCount(mangaId);
    }

    public int getAllUserFollowMangaCount(Long userId) {
        return userFollowRepository.findMangaByUserIdCount(userId);
    }

    public UserFollowResponseDTO unfollowManga(Long mangaId, Long userId) {
        UserFollowResponseDTO userFollow = userFollowRepository.findUserFollowByUserIdAndMangaId(mangaId, userId);
        System.out.println("userFollow: " + userFollow);
        if (userFollow!=null) {
            userFollowRepository.deleteById(userFollow.getId());
            return userFollow;
        } else {
            return null;
        }
    }

    public Boolean checkDoesUserFollowed(Long mangaId, Long userId) {
        UserFollowResponseDTO userFollow = userFollowRepository.findUserFollowByUserIdAndMangaId(mangaId, userId);
        return userFollow!=null;
    }
}
