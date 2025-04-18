package com.graduation.mangaka.controller;

import com.graduation.mangaka.model.MangaChapter;
import com.graduation.mangaka.service.MangaChapterService;
import com.graduation.mangaka.service.MangaService;
import com.graduation.mangaka.util.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chapter")
public class MangaChapterController {

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    private MangaChapterService mangaChapterService;

    @PostMapping("/{manga_id}/add-chapter")
    public ResponseEntity<?> addChapter(@PathVariable("manga_id") Long manga_id,
                                        @RequestParam("pages") List<MultipartFile> pages,
                                        @RequestParam("chapter_index") int chapterIndex,
                                        @RequestParam("title") String title) {
        List<String> listPages=new ArrayList<>();
        for (MultipartFile page: pages) {
            listPages.add(cloudinaryService.uploadImage(page).getUrl());
        }
        MangaChapter newChapter=mangaChapterService.addChapter(manga_id, listPages, chapterIndex, title);
        if (newChapter == null) {
            return ResponseEntity.badRequest().body("Manga with id " + manga_id + " already exists chapter index " + chapterIndex);
        }
        return ResponseEntity.ok().body(newChapter);
    }

    @GetMapping("{manga_id}/get-chapter/{chapter_index}")
    public ResponseEntity<?> getChapter(@PathVariable("manga_id") Long mangaId,
                                        @PathVariable("chapter_index") int chapterIndex) {
        MangaChapter res=mangaChapterService.getChapter(mangaId, chapterIndex);
        if (res==null) {
            return ResponseEntity.badRequest().body("Chapter not found");
        }
        return ResponseEntity.ok().body(mangaChapterService.getChapter(mangaId, chapterIndex));
    }

    @GetMapping("{manga_id}")
    public ResponseEntity<?> getAllChapter(@PathVariable("manga_id") Long mangaId) {
        System.out.println("mangaId:"+mangaId);
        return ResponseEntity.ok().body(mangaChapterService.getAllChapter(mangaId));
    }
    @PutMapping("{manga_id}/update-chapter/{chapter_index}")
    public ResponseEntity<?> updateChapter(@PathVariable("manga_id") Long mangaId,
                                           @PathVariable("chapter_index") int chapterIndex,
                                           @RequestParam("pages") List<MultipartFile> pages,
                                           @RequestParam("title") String title) {
        List<String> listPages=new ArrayList<>();
        for (MultipartFile page: pages) {
            listPages.add(cloudinaryService.uploadImage(page).getUrl());
        }
        return ResponseEntity.ok().body(mangaChapterService.updateChapter(mangaId, listPages, chapterIndex, title));
    }

    @DeleteMapping("{manga_id}/update-chapter/{chapter_index}")
    public ResponseEntity<?> deleteChapter(@PathVariable("manga_id") Long mangaId,
                                           @PathVariable("chapter_index") int chapterIndex) {
        MangaChapter res=mangaChapterService.getChapter(mangaId, chapterIndex);
        if (res==null) {
            return ResponseEntity.badRequest().body("Chapter not found");
        }
        return ResponseEntity.ok().body(mangaChapterService.deleteChapter(mangaId, chapterIndex));
    }
}
