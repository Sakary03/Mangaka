package com.graduation.mangaka.controller;


import com.graduation.mangaka.dto.request.MangaRequestDTO;
import com.graduation.mangaka.dto.request.SearchMangaDTO;
import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.service.MangaService;
import com.graduation.mangaka.util.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/manga")
public class MangaController {
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    private MangaService mangaService;

    @PostMapping("")
    public ResponseEntity<Manga> CreateManga(@ModelAttribute MangaRequestDTO mangaRequestDTO,
                                             @RequestParam("poster")MultipartFile poster,
                                             @RequestParam("background") MultipartFile background) {
            mangaRequestDTO.setPosterUrl(cloudinaryService.uploadImage(poster).getUrl());
            System.out.println("Upload poster successfully");
            mangaRequestDTO.setBackgroundUrl(cloudinaryService.uploadImage(background).getUrl());
            System.out.println("Upload background successfully");
            return ResponseEntity.ok().body(mangaService.addManga(mangaRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> UpdateManga(@ModelAttribute MangaRequestDTO mangaRequestDTO,
                                         @PathVariable Long id) {
            return ResponseEntity.ok().body(mangaService.updateManga(mangaRequestDTO, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteManga(@PathVariable Long id) {
        return ResponseEntity.ok().body(mangaService.deleteManga(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> GetManga(@RequestParam("offset") int offset,
                                      @RequestParam("limit") int limit) {
        return ResponseEntity.ok().body(mangaService.getAllManga(offset, limit));
    }
    @GetMapping("/search")
    public ResponseEntity<?> SearchManga(@ModelAttribute SearchMangaDTO searchMangaDTO,
                                         @RequestParam("offset") int offset,
                                         @RequestParam("limit") int limit) {
        return ResponseEntity.ok().body(mangaService.searchManga(searchMangaDTO, offset, limit));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> GetMangaById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mangaService.GetMangaById(id));
    }


}
