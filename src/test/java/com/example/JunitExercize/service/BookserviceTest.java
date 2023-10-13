package com.example.JunitExercize.service;

import com.example.JunitExercize.domain.BookRepository;
import com.example.JunitExercize.util.MailSender;
import com.example.JunitExercize.util.MailSenderStub;
import com.example.JunitExercize.web.dto.BookRespDto;
import com.example.JunitExercize.web.dto.BookSaveReqDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
//        assertEquals(dto.getTitle(),bookRespDto.getTitle());
//        assertEquals(dto.getAuthor(),bookRespDto.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }

}
