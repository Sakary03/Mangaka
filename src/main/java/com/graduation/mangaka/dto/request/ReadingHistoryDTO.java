package com.graduation.mangaka.dto.request;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingHistoryDTO {
    @NonNull
    Long userId;
    @Nullable
    Long mangaId;
    @Nullable
    Long chapterId;
}
