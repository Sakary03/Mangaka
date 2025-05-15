
package com.graduation.mangaka.repository;

import com.graduation.mangaka.dto.response.UserFollowResponseDTO;
import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.User;
import com.graduation.mangaka.model.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    @Query("SELECT umf.manga FROM UserFollow umf WHERE umf.user.id = :userId")
    Page<Manga> findMangaByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT umf.user FROM UserFollow umf WHERE umf.manga.id = :mangaId")
    Page<User> findUserFollowManga(@Param("mangaId") Long mangaId, Pageable pageable);

    @Query("SELECT count(umf.manga) FROM UserFollow umf WHERE umf.user.id = :userId")
    int findMangaByUserIdCount(@Param("userId") Long userId);

    @Query("SELECT count(umf.user) FROM UserFollow umf WHERE umf.manga.id = :mangaId")
    int findUserFollowMangaCount(@Param("mangaId") Long mangaId);

    @Query("SELECT new com.graduation.mangaka.dto.response.UserFollowResponseDTO(umf.id, umf.user, umf.manga) " +
            "FROM UserFollow umf WHERE umf.manga.id = :mangaId AND umf.user.id = :userId")
    UserFollowResponseDTO findUserFollowByUserIdAndMangaId(@Param("mangaId") Long mangaId, @Param("userId") Long userId);

}
