package com.example.JunitExercize.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // id 가 pk 가 되고     @GeneratedValue(strategy = GenerationType.IDENTITY) 로 1씩 증가함
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 20, nullable = false)
    private String author;

    @Builder
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
