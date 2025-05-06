package com.graduation.mangaka.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    String username;
    String email;
    String password;
    String name;
    Date date;
    String address;
    String role;
    String avatar;
}
