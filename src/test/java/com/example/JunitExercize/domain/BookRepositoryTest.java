package com.example.JunitExercize.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {
    @Autowired // DI
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void save_test(){
        System.out.println("책등록_TEST 실행");

        //given (데이터 준비)
        String title = "junit5 기초공부";
        String author = "더존도서";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        //when (테스트 실행)
        Book bookPS = bookRepository.save(book);
        //then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }
    // 2. 책 목록 보기

    // 3. 책 한건 보기

    // 4. 책 수정

    // 5. 책 삭제

}
