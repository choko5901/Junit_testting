package com.example.JunitExercize.service;

import com.example.JunitExercize.domain.Book;
import com.example.JunitExercize.domain.BookRepository;
import com.example.JunitExercize.web.dto.BookRespDto;
import com.example.JunitExercize.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto dto){
        Book bookPS  = bookRepository.save(dto.toEntity());
        return new BookRespDto().toDto(bookPS);
    }

    // 2. 책 목록보기
    public List<BookRespDto> 책목록보기(){
        return bookRepository.findAll().stream()
                .map(new BookRespDto() :: toDto)
                .collect(Collectors.toList());
    }

    // 3. 책한권 보기

    // 4. 책 삭제

    // 5. 책 수정
}
