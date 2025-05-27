package com.graduation.mangaka.dto.request;
import com.graduation.mangaka.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    String username;
    String email;
    String name;
    Date date;
    String address;
    String role;
    String avatar;

    public User userRequestDtoToUser() {
        User user = new User();
        user.setUserName(this.username);
        user.setEmail(this.email);
        user.setDob(this.date);
        user.setFullName(this.name);
        user.setAvatarUrl(this.avatar);
        user.setAddress(this.address);
        return user;
    }
}