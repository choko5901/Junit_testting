package com.example.JunitExercize.service;

import com.example.JunitExercize.domain.Book;
import com.example.JunitExercize.domain.BookRepository;
import com.example.JunitExercize.util.MailSender;
import com.example.JunitExercize.web.dto.response.BookListRespDto;
import com.example.JunitExercize.web.dto.response.BookRespDto;
import com.example.JunitExercize.web.dto.request.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mainSender;
    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto dto){
        Book bookPS  = bookRepository.save(dto.toEntity());
            if(bookPS != null){
                if(!mainSender.send()){
                    throw new RuntimeException("메일이 전송되지 않았습니다");
                }
            }
        return bookPS.toDto();
    }

    // 2. 책 목록보기
    public BookListRespDto 책목록보기(){
       List<BookRespDto> dtos = bookRepository.findAll().stream()
                .map(Book :: toDto) // 객체 하나를 꺼내서 toDto타게끔 하는 메소드 참조식 문법
//                .map((bookPS) -> new BookRespDto().toDto(bookPS))
                .collect(Collectors.toList());
        BookListRespDto bookListRespDto = BookListRespDto.builder().bookList(dtos).build();

        return bookListRespDto;
    }

    // 3. 책한권 보기
    public BookRespDto 책한권보기(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            Book bookPS = bookOP.get();
            return bookPS.toDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id){
        bookRepository.deleteById(id);
    }

    // 5. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책수정하기(Long id , BookSaveReqDto dto){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    } // 메소드 종료시에 더티 채킹(flush)으로 update 됩니다.

}
