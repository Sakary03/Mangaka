package com.graduation.mangaka.dto.response;

import com.graduation.mangaka.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long userID;
    private String username;
    private String name;
    private String dob;
    private String email;
    private String avatar;
    private String role;

    public UserInfoResponse(User user) {
        this.username = user.getUserName();
        this.name = user.getFullName();
        this.dob = user.getDob().toString();
        this.email = user.getEmail();
        this.avatar = user.getAvatarUrl();
        this.role = user.getRole().toString();
        this.userID = user.getId();
    }
}
