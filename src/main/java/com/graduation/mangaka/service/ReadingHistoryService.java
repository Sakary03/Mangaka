package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.ReadingHistoryDTO;
import com.graduation.mangaka.dto.response.ChapterDTO;
import com.graduation.mangaka.dto.response.MangaDTO;
import com.graduation.mangaka.dto.response.ReadingHistoryResponseDTO;
import com.graduation.mangaka.dto.response.UserDTO;
import com.graduation.mangaka.model.ReadingHistory;
import com.graduation.mangaka.repository.MangaChapterRepository;
import com.graduation.mangaka.repository.MangaRepository;
import com.graduation.mangaka.repository.ReadingHistoryRepository;
import com.graduation.mangaka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingHistoryService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MangaRepository mangaRepository;
    @Autowired
    ReadingHistoryRepository readingHistoryRepository;
    @Autowired
    MangaChapterRepository mangaChapterRepository;

    public ReadingHistory CreateReadingHistory(ReadingHistoryDTO readingHistoryDTO) {
        ReadingHistory readingHistory = new ReadingHistory();
        if (readingHistoryDTO.getUserId()!=null && readingHistoryDTO.getMangaId()!=null && readingHistoryDTO.getChapterId()!=null) {
            ReadingHistory checkExist = readingHistoryRepository.findByMangaIdAndChapterIdAndUserId(readingHistoryDTO.getMangaId(), readingHistoryDTO.getChapterId(), readingHistoryDTO.getUserId());
            System.out.println("Check exist: " + checkExist);
            if (checkExist!=null) return checkExist;
        }
        readingHistory.setUser(userRepository.findById(readingHistoryDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        readingHistory.setManga(mangaRepository.findById(readingHistoryDTO.getMangaId()).orElseThrow(() -> new RuntimeException("Manga not found")));
        readingHistory.setChapter(mangaChapterRepository.findById(readingHistoryDTO.getChapterId()).orElseThrow(() -> new RuntimeException("Manga chapter not found")));
        readingHistoryRepository.save(readingHistory);
        return readingHistory;
    }

    public List<ReadingHistoryResponseDTO> GetReadingHistory(ReadingHistoryDTO readingHistoryDTO) {
        List<ReadingHistory> histories;

        if (readingHistoryDTO.getUserId() == null) {
            throw new RuntimeException("UserId is null");
        }

        if (readingHistoryDTO.getMangaId() == null) {
            histories = readingHistoryRepository.findByUserIdOrderByCreatedAtDesc(readingHistoryDTO.getUserId());
        } else {
            histories = readingHistoryRepository.findByMangaIdAndUserIdOrderByCreatedAt(
                    readingHistoryDTO.getMangaId(), readingHistoryDTO.getUserId());
        }

        return histories.stream().map(this::convertToDTO).toList();
    }


    private ReadingHistoryResponseDTO convertToDTO(ReadingHistory history) {
        ReadingHistoryResponseDTO dto = new ReadingHistoryResponseDTO();
        dto.setId(history.getId());
        dto.setCreatedAt(history.getCreatedAt());

        // User DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(history.getUser().getId());
        userDTO.setUsername(history.getUser().getFullName());
        dto.setUser(userDTO);

        // Manga DTO
        MangaDTO mangaDTO = new MangaDTO();
        mangaDTO.setId(history.getManga().getId());
        mangaDTO.setTitle(history.getManga().getTitle());
        dto.setManga(mangaDTO);

        // Chapter DTO
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setId(history.getChapter().getId());
        chapterDTO.setTitle(history.getChapter().getTitle());
        chapterDTO.setNumber(history.getChapter().getChapterIndex());
        dto.setChapter(chapterDTO);

        return dto;
    }


}
