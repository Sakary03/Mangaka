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
public class LoginResponseDTO {
    private UserInfoResponse userInfo;
    private String accessToken;
    public void setUserInfo(User user) {
        this.userInfo=new UserInfoResponse(user);
    }
}
