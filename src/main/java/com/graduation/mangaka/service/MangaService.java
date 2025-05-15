package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.MangaRequestDTO;
import com.graduation.mangaka.dto.request.SearchMangaDTO;
import com.graduation.mangaka.model.TypeAndRole.Genres;
import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.TypeAndRole.Genres;
import com.graduation.mangaka.repository.MangaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MangaService {
    private static final Logger logger = LoggerFactory.getLogger(MangaService.class);

    @Autowired
    private MangaRepository mangaRepository;

    public Manga addManga(MangaRequestDTO mangaRequestDTO) {
        try {
            Manga manga = mangaRequestDTO.toManga();
            return mangaRepository.save(manga);
        } catch (Exception e) {
            logger.error("Error while adding manga", e);
            return null;
        }
    }

    public Manga updateManga(MangaRequestDTO mangaRequestDTO, Long id) {
        try {
            Manga manga = mangaRepository.findById(id).orElseThrow(() -> new RuntimeException("Manga not found"));

            if (mangaRequestDTO.getAuthor() != null) manga.setAuthor(mangaRequestDTO.getAuthor());
            if (mangaRequestDTO.getTitle() != null) manga.setTitle(mangaRequestDTO.getTitle());
            if (mangaRequestDTO.getOverview() != null) manga.setOverview(mangaRequestDTO.getOverview());
            if (mangaRequestDTO.getDescription() != null) manga.setDescription(mangaRequestDTO.getDescription());
            if (mangaRequestDTO.getGenres()!=null) {
                manga.setGenres(new ArrayList<>());
                for (String genre : mangaRequestDTO.getGenres()) {
                    manga.addGenres(genre);
                }
            }
            if (mangaRequestDTO.getBackgroundUrl() != null) manga.setBackgroundUrl(mangaRequestDTO.getBackgroundUrl());
            if (mangaRequestDTO.getPosterUrl() != null) manga.setPosterUrl(mangaRequestDTO.getPosterUrl());
            return mangaRepository.save(manga);
        } catch (Exception e) {
            logger.error("Error while updating manga", e);
            return null;
        }
    }

    public String deleteManga(Long id) {
        Manga manga = mangaRepository.findById(id).orElseThrow(() -> new RuntimeException("Manga not found"));
        try {
            mangaRepository.delete(manga);
            return "Manga deleted successfully";
        } catch (Exception e) {
            logger.error("Error while deleting manga", e);
            return "Error while deleting manga";
        }
    }

    public List<Manga> getAllManga(int offset, int limit, String field, boolean isAsc) {
        Page<Manga> mangaPage;
        if (isAsc) mangaPage = mangaRepository.findAll(PageRequest.of(offset, limit, Sort.by(field).ascending()));
        else mangaPage = mangaRepository.findAll(PageRequest.of(offset, limit, Sort.by(field).descending()));
        return mangaPage.getContent();
    }

    public List<Manga> searchManga(SearchMangaDTO searchMangaDTO, int offset, int limit) {
        Specification<Manga> spec = buildSpecification(
                searchMangaDTO.getTitle(),
                searchMangaDTO.getAuthor(),
                searchMangaDTO.getGenres()
        );
        Page<Manga> mangaPage = mangaRepository.findAll(spec, PageRequest.of(offset, limit));
        return mangaPage.getContent();
    }

    private Specification<Manga> buildSpecification(String title, String author, List<String> genres) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                ));
            }

            if (author != null && !author.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")),
                        "%" + author.toLowerCase() + "%"
                ));
            }

            if (genres != null && !genres.isEmpty()) {
                for (String genreStr : genres) {
                    try {
                        Genres genreEnum = Genres.valueOf(genreStr.toUpperCase());
                        // Ensures that EACH genreEnum is a member of the genres collection
                        predicates.add(criteriaBuilder.isMember(genreEnum, root.get("genres")));
                    } catch (IllegalArgumentException e) {
                        logger.warn("Invalid genre provided: {}", genreStr);
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public Manga GetMangaById(Long id) {
        return mangaRepository.findById(id).orElseThrow(()-> new RuntimeException("Manga not found"));
    }

    public Manga IncreaseReadTimes(Long id) {
        Manga manga = mangaRepository.findById(id).orElseThrow(()-> new RuntimeException("Manga not found"));
        manga.setReadTimes(manga.getReadTimes()+1);
        return mangaRepository.save(manga);
    }

    public List<Manga> SearchManga(SearchMangaDTO params) {
        if (params.getTitle() != null && !params.getTitle().isEmpty()) {}
        return null;
    }
}
