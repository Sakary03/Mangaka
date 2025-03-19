package com.graduation.mangaka.model.TypeAndRole;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    SHONEN("Shonen", "Action-packed stories aimed at young boys"),
    SHOJO("Shojo", "Romance and drama-focused manga for young girls"),
    SEINEN("Seinen", "Mature and complex themes for adult men"),
    JOSEI("Josei", "Realistic romance and drama for adult women"),
    ISEKAI("Isekai", "Transported to another world adventures"),
    MECHA("Mecha", "Giant robots and futuristic warfare"),
    SLICE_OF_LIFE("Slice of Life", "Everyday life with a touch of drama"),
    FANTASY("Fantasy", "Magic, mythical creatures, and supernatural elements"),
    SCI_FI("Sci-Fi", "Futuristic and science-based stories"),
    HORROR("Horror", "Thrilling and scary supernatural tales"),
    MYSTERY("Mystery", "Crime-solving and detective stories"),
    SUPERNATURAL("Supernatural", "Ghosts, spirits, and otherworldly beings"),
    ROMANCE("Romance", "Love stories with emotional depth"),
    COMEDY("Comedy", "Humorous and lighthearted storytelling"),
    SPORTS("Sports", "Competitive and inspiring sports narratives"),
    HISTORICAL("Historical", "Stories set in historical periods"),
    MARTIAL_ARTS("Martial Arts", "Fighting and combat-focused storytelling"),
    PSYCHOLOGICAL("Psychological", "Mind games, suspense, and deep themes"),
    MUSIC("Music", "Stories centered around musicians and bands"),
    ADVENTURE("Adventure", "Exciting journeys and explorations"),
    HAREM("Harem", "One protagonist with multiple love interests"),
    REVERSE_HAREM("Reverse Harem", "A female protagonist with many male admirers"),
    GAME("Game", "Stories about gaming, VR, and competitions"),
    DEMONS("Demons", "Battles against demons and dark forces"),
    VAMPIRE("Vampire", "Stories featuring vampires and gothic elements");

    private final String name;
    private final String description;
}

