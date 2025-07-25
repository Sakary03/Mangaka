package com.graduation.mangaka.repository;

import com.graduation.mangaka.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserName(String username);
    public User findByEmail(String email);
    public Boolean existsByEmail(String email);
    public Boolean existsByUserName(String username);
    public Page<User> findAll(Pageable pageable);
    public User getUserById(long id);
    public User findByPasswordResetToken(String token);
}
