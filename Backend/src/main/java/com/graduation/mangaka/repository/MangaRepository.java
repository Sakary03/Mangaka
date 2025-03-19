package com.graduation.mangaka.repository;

import com.graduation.mangaka.model.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {
}
