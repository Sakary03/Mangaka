package com.graduation.mangaka.controller;

import com.graduation.mangaka.dto.request.UserRequestDTO;
import com.graduation.mangaka.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<?> GetUserInfo(@PathVariable Long id){
        return ResponseEntity.ok(userService.GetUserInfo(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> GetAllUserInfo(@RequestParam int offset, @RequestParam int limit){
        return ResponseEntity.ok().body(userService.GetAllUserByBatch(offset, limit));
    }
    @PostMapping("/{id}/update")
    public ResponseEntity<?> UpdateUser(@RequestBody UserRequestDTO userRequestDTO,
                                     @PathVariable Long id){
        return ResponseEntity.ok().body(userService.UpdateUser(userRequestDTO, id));
    }
}
