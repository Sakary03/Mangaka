package com.graduation.mangaka.repository;

import com.graduation.mangaka.model.ReadingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingHistoryRepository extends JpaRepository<ReadingHistory, Long> {
    List<ReadingHistory> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<ReadingHistory> findByMangaIdAndUserIdOrderByCreatedAt(Long mangaId, Long userId);
    ReadingHistory findByMangaIdAndChapterIdAndUserId(Long mangaId, Long chapterId, Long userId);
}
