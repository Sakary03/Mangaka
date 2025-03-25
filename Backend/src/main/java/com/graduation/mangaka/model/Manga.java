package com.graduation.mangaka.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.graduation.mangaka.model.TypeAndRole.Genres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="manga")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String overview;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String author;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String posterUrl;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String backgroundUrl;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MangaChapter> chapters;

    @ElementCollection
    @CollectionTable(name = "manga_genres", joinColumns = @JoinColumn(name = "manga_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private List<Genres> genres;

    public List<Genres> addGenres(String genre) {
        if (this.genres == null) {
            this.genres = new ArrayList<>();
        }
        genres.add(Genres.valueOf(genre));
        return genres;
    }
}
