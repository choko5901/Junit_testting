package com.example.JunitExercize.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class BookListRespDto {
    List<BookRespDto> itmes;
    @Builder
    public BookListRespDto(List<BookRespDto> bookList) {
        this.itmes = bookList;
    }
}
