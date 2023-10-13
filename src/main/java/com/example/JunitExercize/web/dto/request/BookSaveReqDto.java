package com.example.JunitExercize.web.dto.request;

import com.example.JunitExercize.domain.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter // Controller 에서 Setter가 호출 되면서 Dto에 값이 채워짐.
@Getter
public class BookSaveReqDto {
    @NotBlank
    @Size(min =1, max =50)
    private String title;
    @NotBlank
    @Size(min =1, max =20)
    private String author;


    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }

}
