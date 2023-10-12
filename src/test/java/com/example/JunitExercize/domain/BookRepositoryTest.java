package com.example.JunitExercize.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {
    @Autowired // DI
    private BookRepository bookRepository;

    //@BeforeAll // 테스트 시작전 한번만 수행
    @BeforeEach // 각 테스트 메소드 시작전 매번 시행
    public void 데이터준비(){
        String title = "junit5 기초공부";
        String author = "더존도서";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    } // 보통 하나의 테스트 메소드가 끝나면 트렌젝션이 끝나는데
    //BeforeEach 는 끝날까? 유지가 될까?
    // 가정 1 : [데이터 준비() +1 책등록(T)] ,[ 데이터 준비() +2 첵목록보기 (T)] --> 사이즈 1 요게 정답이다 !
    // 가정 2 :[ 데이터준비 () + 1 책등록 , 데티어 준비() + 2 첵목록보기 () ] --> 사이즈 2

    // 1. 책 등록
    @Test
    public void save_test(){
        System.out.println("책등록_TEST 실행");

        //given (데이터 준비)
        String title = "junit";
        String author = "비즈온";
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
    // 위의 테스트가 끝날때 트렌젝션이 종료되어 저장된 데이터를 초기화 합니다
    // 그래서 밑에

    // 2. 책 목록 보기
    @Test
    public void 책목록보기_test(){
        //given
        String title = "junit5 기초공부";
        String author = "더존도서";
        // when
        List<Book> booksPS = bookRepository.findAll();

        // 트렌젝션 종료 시점을 알아보기 위한 검증
        System.out.println("사이즈 ===============================" + booksPS.size());

        // then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }


    // 3. 책 한건 보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한권보기_test(){
        //given
        String title = "junit5 기초공부";
        String author = "더존도서";
        // when
        Book bookPS = bookRepository.findById(1L).get();
        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_test(){
        // given
        Long id = 1L;
        // when
        bookRepository.deleteById(id);
        // then
        assertFalse(bookRepository.findById(id).isPresent());
    }

    // 5. 책 수정

}