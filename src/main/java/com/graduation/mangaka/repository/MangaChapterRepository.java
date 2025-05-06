package com.graduation.mangaka.repository;

import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.MangaChapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaChapterRepository extends JpaRepository<MangaChapter, Long> {
    List<MangaChapter> findMangaChapterByMangaId(Long mangaId);
    MangaChapter findMangaChapterByMangaIdAndChapterIndex(Long mangaId, int chapterIndex);
    Page<MangaChapter> findAll(Pageable pageable);
}
