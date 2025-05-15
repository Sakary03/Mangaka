package com.graduation.mangaka.dto.response;

import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowResponseDTO {
    Long Id;
    User user;
    Manga manga;
}
