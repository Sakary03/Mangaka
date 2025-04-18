package com.graduation.mangaka.service;

import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.MangaChapter;
import com.graduation.mangaka.repository.MangaChapterRepository;
import com.graduation.mangaka.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaChapterService {
    @Autowired
    private MangaRepository mangaRepository;
    @Autowired
    private MangaChapterRepository mangaChapterRepository;

    public MangaChapter addChapter(Long mangaId, List<String> pages, int chapterIndex, String title) {
        Manga manga=mangaRepository.findById(mangaId).orElseThrow(() -> new RuntimeException("manga not found"));
        MangaChapter checkChapter=mangaChapterRepository.findMangaChapterByMangaIdAndChapterIndex(mangaId, chapterIndex);
        if (checkChapter!=null) {
            return null;
        }
        MangaChapter mangaChapter=new MangaChapter();
        mangaChapter.setManga(manga);
        mangaChapter.setPages(pages);
        mangaChapter.setChapterIndex(chapterIndex);
        if (title==null || title.isEmpty()) {
            mangaChapter.setTitle("Chapter " + chapterIndex);
        } else {
            System.out.println("Bad case");
            System.out.println("The title: " + title + " is bad");
            mangaChapter.setTitle(title);
        }
        return mangaChapterRepository.save(mangaChapter);
    }

    public MangaChapter getChapter(Long mangaId, int chapterIndex) {
        return mangaChapterRepository.findMangaChapterByMangaIdAndChapterIndex(mangaId, chapterIndex);
    }
    public List<MangaChapter> getAllChapter(Long mangaId) {
        return mangaChapterRepository.findMangaChapterByMangaId(mangaId);
    }

    public MangaChapter updateChapter(Long mangaId, List<String> pages, int chapterIndex, String title
    ) {
        MangaChapter mangaChapter=mangaChapterRepository.findMangaChapterByMangaIdAndChapterIndex(mangaId, chapterIndex);
        if (mangaChapter==null) {
            return addChapter(mangaId, pages, chapterIndex, title);
        } else {
            mangaChapter.setPages(pages);
            mangaChapterRepository.save(mangaChapter);
            return mangaChapter;
        }
    }

    public String deleteChapter(Long mangaId, int chapterIndex) {
        MangaChapter mangaChapter=mangaChapterRepository.findMangaChapterByMangaIdAndChapterIndex(mangaId, chapterIndex);
        if (mangaChapter!=null) mangaChapterRepository.deleteById(mangaChapter.getId());
        return "Chapter delete successfully";
    }
}
