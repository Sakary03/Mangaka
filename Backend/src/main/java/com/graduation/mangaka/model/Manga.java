package com.graduation.mangaka.model;

import com.graduation.mangaka.model.TypeAndRole.Genres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
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

    @Column(nullable = false)
    private String overview;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String author;

    @Column(nullable = true)
    private String posterUrl;

    @Column(nullable = true)
    private String backgroundUrl;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MangaChapter> chapters;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "genres", columnDefinition = "text[]")
    private List<Genres> genres;

    public List<Genres> addGenres(String genre) {
        this.genres.add(Genres.valueOf(genre));
        return this.genres;
    }
}
