package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.ReadingHistoryDTO;
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

    public List<ReadingHistory> GetReadingHistory(ReadingHistoryDTO readingHistoryDTO) {
        if (readingHistoryDTO.getUserId()==null) {
            throw new RuntimeException("UserId is null");
        }
        if (readingHistoryDTO.getMangaId()==null) {
            return readingHistoryRepository.findByUserIdOrderByCreatedAtDesc(readingHistoryDTO.getUserId());
        } else {
            return readingHistoryRepository.findByMangaIdAndUserIdOrderByCreatedAt(readingHistoryDTO.getMangaId(), readingHistoryDTO.getUserId());
        }
    }
}
