package com.example.JunitExercize.service;

import com.example.JunitExercize.domain.Book;
import com.example.JunitExercize.domain.BookRepository;
import com.example.JunitExercize.util.MailSender;
import com.example.JunitExercize.web.dto.response.BookListRespDto;
import com.example.JunitExercize.web.dto.response.BookRespDto;
import com.example.JunitExercize.web.dto.request.BookSaveReqDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 가짜 환경 만들기 위해
public class BookserviceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;
    @InjectMocks
    private BookService bookService;

    @Test
    public void 책등록하기_테스트(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Junit 기초");
        dto.setAuthor("더존 출판사");
        //stub(가짜 환경 행동 정의라고 생각하면 됩니다.)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        //when
        BookRespDto bookRespDto = bookService.책등록하기(dto);
        //then
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }

    @Test
    public void 책목록보기_테스트(){
        //given

        //stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit 기초", "더존 출판사"));
        books.add(new Book(2L, "Spring 기초", "비즈온 출판사"));
        when(bookRepository.findAll()).thenReturn(books);

        //when
        BookListRespDto bookListRespDto = bookService.책목록보기();
        //then
        assertThat(bookListRespDto.getItems().get(0).getTitle()).isEqualTo("junit 기초");
        assertThat(bookListRespDto.getItems().get(0).getAuthor()).isEqualTo("더존 출판사");
        assertThat(bookListRespDto.getItems().get(1).getTitle()).isEqualTo("Spring 기초");
        assertThat(bookListRespDto.getItems().get(1).getAuthor()).isEqualTo("비즈온 출판사");
    }

    @Test
    public void 책한권보기_테스트(){
        //given
        Long id = 1L;
        Book book = new Book(1L, "Junit 기초", "황작가");
        Optional<Book> bookOp = Optional.of(book);
        //stub
        when(bookRepository.findById(id)).thenReturn(bookOp);

        //when
        BookRespDto bookRespDto = bookService.책한권보기(id);
        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기_테스트(){
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Mqtt 기초");
        dto.setAuthor("비즈온 출판사");

        // stub
        Book book = new Book(1L, "Junit 기초", "더존 출판사");
        Optional<Book> bookOp = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOp);

        // when
        BookRespDto bookRespDto = bookService.책수정하기(id, dto);
        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());

    }

}
