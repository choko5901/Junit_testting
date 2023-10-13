package com.example.JunitExercize.service;

import com.example.JunitExercize.domain.BookRepository;
import com.example.JunitExercize.util.MailSenderStub;
import com.example.JunitExercize.web.dto.BookRespDto;
import com.example.JunitExercize.web.dto.BookSaveReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookserviceTest {
    @Autowired
    private BookRepository bookRepository;
    @Test
    public void 책등록하기_테스트(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Junit 기초");
        dto.setAuthor("더존 출판사");
        //stub
        MailSenderStub mailSenderStub = new MailSenderStub();
        //when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.책등록하기(dto);
        //then
        assertEquals(dto.getTitle(),bookRespDto.getTitle());
        assertEquals(dto.getAuthor(),bookRespDto.getAuthor());

    }

}
