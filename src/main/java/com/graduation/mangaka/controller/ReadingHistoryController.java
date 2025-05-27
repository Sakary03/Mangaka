package com.graduation.mangaka.controller;

import com.graduation.mangaka.dto.request.ReadingHistoryDTO;
import com.graduation.mangaka.service.ReadingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class ReadingHistoryController {
    @Autowired
    ReadingHistoryService readingHistoryService;

    @PostMapping("")
    public ResponseEntity<?> CreateReadingHistory(@ModelAttribute ReadingHistoryDTO readingHistoryDTO) {
        return ResponseEntity.ok().body(readingHistoryService.CreateReadingHistory(readingHistoryDTO));
    }
    @GetMapping("")
    public ResponseEntity<?> GetReadingHistory(@ModelAttribute ReadingHistoryDTO readingHistoryDTO) {
        return ResponseEntity.ok().body(readingHistoryService.GetReadingHistory(readingHistoryDTO));
    }
}
