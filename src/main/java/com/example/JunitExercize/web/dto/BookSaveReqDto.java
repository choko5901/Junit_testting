package com.example.JunitExercize.web.dto;

import com.example.JunitExercize.domain.Book;
import lombok.Setter;

@Setter // Controller 에서 Setter가 호출 되면서 Dto에 값이 채워짐.
public class BookSaveReqDto {
    private String title;
    private String author;


    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }

}
