package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.MangaRequestDTO;
import com.graduation.mangaka.dto.request.SearchMangaDTO;
import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.TypeAndRole.Genres;
import com.graduation.mangaka.model.TypeAndRole.MangaStatus;
import com.graduation.mangaka.model.TypeAndRole.UserRole;
import com.graduation.mangaka.model.User;
import com.graduation.mangaka.repository.MangaRepository;
import com.graduation.mangaka.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MangaService {
    private static final Logger logger = LoggerFactory.getLogger(MangaService.class);

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private UserRepository userRepository;

    public static List<Genres> getRandomGenres() {
        List<Genres> allGenres = new ArrayList<>(Arrays.asList(Genres.values()));
        Collections.shuffle(allGenres);

        int count = new Random().nextInt(3) + 6;

        return allGenres.subList(0, count);
    }

    public Manga addManga(MangaRequestDTO mangaRequestDTO) {
        try {
            if (mangaRequestDTO.getUserId() == null) {
                throw new RuntimeException("User id is required");
            }
            final Long userId = mangaRequestDTO.getUserId();
            final User user =  userRepository.findById(mangaRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Manga manga = mangaRequestDTO.toManga();
            List<Genres> genres = getRandomGenres();
            manga.setGenres(genres);

            if (user.getRole().equals(UserRole.USER)) {
                manga.setUploadedBy(user);
                manga.setStatus(MangaStatus.PENDING);
            } else {
                manga.setUploadedBy(user);
                manga.setStatus(MangaStatus.APPROVED);
            }
            return mangaRepository.save(manga);
        } catch (Exception e) {
            logger.error("Error while adding manga", e);
            return null;
        }
    }

    public Manga updateManga(MangaRequestDTO mangaRequestDTO, Long id) {
        try {
            Manga manga = mangaRepository.findById(id).orElseThrow(() -> new RuntimeException("Manga not found"));
            User user = userRepository.findById(mangaRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            if (mangaRequestDTO.getUserId() != user.getId()) {
                throw new RuntimeException("User not authorized to update this manga");
            }
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
            if (user.getRole().equals(UserRole.USER)) {
                manga.setStatus(MangaStatus.UPDATE);
            } else {
                manga.setStatus(MangaStatus.APPROVED);
            }
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

    public List<Manga> getAllManga(int offset, int limit, String field, boolean isAsc, Authentication authentication) {
        Page<Manga> mangaPage;
        
        boolean isAdmin = isUserAdmin(authentication);
        
        if (isAsc) {
            if (isAdmin) {
                mangaPage = mangaRepository.findAll(PageRequest.of(offset, limit, Sort.by(field).ascending()));
            } else {
                mangaPage = mangaRepository.findByStatus(MangaStatus.APPROVED, PageRequest.of(offset, limit, Sort.by(field).ascending()));
            }
        } else {
            if (isAdmin) {
                mangaPage = mangaRepository.findAll(PageRequest.of(offset, limit, Sort.by(field).descending()));
            } else {
                mangaPage = mangaRepository.findByStatus(MangaStatus.APPROVED, PageRequest.of(offset, limit, Sort.by(field).descending()));
            }
        }
        
        return mangaPage.getContent();
    }

    public List<Manga> searchManga(SearchMangaDTO searchMangaDTO, int offset, int limit, Authentication authentication) {
        boolean isAdmin = isUserAdmin(authentication);
        Specification<Manga> spec;
        if (searchMangaDTO.getUploadedBy() != null) {
            spec = buildSpecification(
                    searchMangaDTO.getTitle(),
                    searchMangaDTO.getAuthor(),
                    searchMangaDTO.getGenres(),
                    searchMangaDTO.getStatus(),
                    searchMangaDTO.getUploadedBy(),
                    true
            );
        } else {
            spec = buildSpecification(
                    searchMangaDTO.getTitle(),
                    searchMangaDTO.getAuthor(),
                    searchMangaDTO.getGenres(),
                    searchMangaDTO.getStatus(),
                    searchMangaDTO.getUploadedBy(),
                    isAdmin
            );
        }
        Page<Manga> mangaPage = mangaRepository.findAll(spec, PageRequest.of(offset, limit));
        return mangaPage.getContent();
    }

    private boolean isUserAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        
        return user != null && user.getRole() == UserRole.ADMIN;
    }

    private Long userIdByAuthenToken(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        return user.getId();
    }

    private Specification<Manga> buildSpecification(
            String title,
            String author,
            List<String> genres,
            List<String> statusList,
            Long uploadedByUserId,
            boolean isAdmin
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add status filter for non-admin users
            if (!isAdmin) {
                predicates.add(criteriaBuilder.equal(root.get("status"), MangaStatus.APPROVED));
            }

            if (title != null && !title.isEmpty() && title.equals(author)) {
                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );

                Predicate authorPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")),
                        "%" + title.toLowerCase() + "%"
                );

                predicates.add(criteriaBuilder.or(titlePredicate, authorPredicate));
            } else {
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
            }

            if (genres != null && !genres.isEmpty()) {
                for (String genreStr : genres) {
                    try {
                        Genres genreEnum = Genres.valueOf(genreStr.toUpperCase());
                        predicates.add(criteriaBuilder.isMember(genreEnum, root.get("genres")));
                    } catch (IllegalArgumentException e) {
                        logger.warn("Invalid genre provided: {}", genreStr);
                    }
                }
            }

            // Only allow admin users to filter by status in search
            if (statusList != null && !statusList.isEmpty() && isAdmin) {
                List<MangaStatus> validStatuses = statusList.stream()
                        .map(s -> {
                            try {
                                return MangaStatus.valueOf(s.toUpperCase());
                            } catch (IllegalArgumentException e) {
                                logger.warn("Invalid manga status provided: {}", s);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .toList();

                if (!validStatuses.isEmpty()) {
                    predicates.add(root.get("status").in(validStatuses));
                }
            }

            if (uploadedByUserId != null) {
                predicates.add(criteriaBuilder.equal(root.get("uploadedBy").get("id"), uploadedByUserId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }



    public Manga GetMangaById(Long id) {
        return mangaRepository.findById(id).orElseThrow(()-> new RuntimeException("Manga not found"));
    }

    public Manga IncreaseReadTimes(Long id) {
        logger.info("Increase read times for manga id: {}", id);
        Manga manga = mangaRepository.findById(id).orElseThrow(()-> new RuntimeException("Manga not found"));
        manga.setReadTimes(manga.getReadTimes()+1);
        return mangaRepository.save(manga);
    }

    public List<Manga> SearchManga(SearchMangaDTO params) {
        if (params.getTitle() != null && !params.getTitle().isEmpty()) {}
        return null;
    }

    public Manga ChangeMangaStatus(Long id, String status) {
        Manga manga = mangaRepository.findById(id).orElseThrow(()-> new RuntimeException("Manga not found"));
        manga.setStatus(MangaStatus.valueOf(status.toUpperCase()));
        mangaRepository.save(manga);
        return manga;
    }
}
