package com.graduation.mangaka.repository;

import com.graduation.mangaka.model.Manga;
import com.graduation.mangaka.model.TypeAndRole.MangaStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long>, JpaSpecificationExecutor<Manga> {
    Manga getMangaById(Long id);
    Page<Manga> findByStatus(MangaStatus status, Pageable pageable);
}
