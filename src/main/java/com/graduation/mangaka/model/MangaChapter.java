package com.graduation.mangaka.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "manga_chapter",
        uniqueConstraints = @UniqueConstraint(columnNames = {"manga_id", "chapterIndex"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MangaChapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private int chapterIndex;

    @ElementCollection
    @CollectionTable(name = "chapter_pages", joinColumns = @JoinColumn(name = "chapter_id"))
    @Column(name = "page_url")
    private List<String> pages;

    @Column(name="title")
    private String title;

    @Column(nullable = false)
    private int readTimes = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id", nullable = false)
    @JsonIgnore
    private Manga manga;

    @PostConstruct
    public void init() {
        if (this.title == null) {
            this.title = "Chapter "+ String.valueOf(this.chapterIndex);
        }
    }
}
