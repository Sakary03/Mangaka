package com.graduation.mangaka.dto.request;

import com.graduation.mangaka.model.Manga;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MangaRequestDTO {
    @NotBlank(message = "Title is required")
    String title;
    @NotBlank(message = "Author is required")
    String author;
    @NotBlank(message = "Description is required")
    String description;
    @NotBlank(message = "Overview is required")
    String overview;
    @NotBlank(message = "Genres is required")
    List<String> genres;

    String posterUrl;
    String backgroundUrl;

    @NotBlank(message = "UserId is required")
    Long userId;

    public Manga toManga(){
        Manga manga = new Manga();
        manga.setTitle(title);
        manga.setAuthor(author);
        manga.setDescription(description);
        manga.setOverview(overview);
        manga.setPosterUrl(posterUrl);
        manga.setBackgroundUrl(backgroundUrl);
        System.out.println("Number of genre: "+ genres.size());
        for (String genre : genres) {
            System.out.print("Added genre: "+ genre);
            manga.addGenres(genre);
        }
        return manga;
    }
}
