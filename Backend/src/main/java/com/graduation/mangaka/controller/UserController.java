package com.graduation.mangaka.controller;

import com.graduation.mangaka.dto.request.UserRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/")
    public String CreateUser(@RequestBody UserRequestDTO user) {
        return "userAdded";
    }
}
