package com.example.JunitExercize.web;

import com.example.JunitExercize.service.BookService;
import com.example.JunitExercize.web.dto.response.BookListRespDto;
import com.example.JunitExercize.web.dto.response.BookRespDto;
import com.example.JunitExercize.web.dto.request.BookSaveReqDto;
import com.example.JunitExercize.web.dto.response.CMRRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    //1. 책등록
    // Json타입을 받는다고 가정화면 바디에 담아 전달 받을터이니 @RequestBody를 사용한다
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult){
        // 추후에 AOP 처리하면 좋은 로직임
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for (FieldError fe:
                 bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(),fe.getDefaultMessage());
            }
            throw  new RuntimeException(errorMap.toString());

        }

        BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);
        return new ResponseEntity<>(CMRRespDto.builder().code(1).msg("책 저장 성공").body(bookRespDto).build(), HttpStatus.CREATED); // 인서트 성공하면 201 코드 날려주는 상태
    }

    //2. 책목록 보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList(){
        BookListRespDto bookListRespDto = bookService.책목록보기();
        return new ResponseEntity<>(CMRRespDto.builder().code(1).msg("책 목록보기 성공").body(bookListRespDto).build(), HttpStatus.OK); // 성공하면 200 코드 날려주는 상태
    }
    //3. 책한권 보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id){
        BookRespDto bookRespDto = bookService.책한권보기(id);
        return new ResponseEntity<>(CMRRespDto.builder().code(1).msg("책 한 권 보기 성공").body(bookRespDto).build(), HttpStatus.OK); // 성공하면 200 코드 날려주는 상태
    }
    //4. 책 삭제 하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.책삭제하기(id);
        return new ResponseEntity<>(CMRRespDto.builder().code(1).msg("책 삭제하기 성공").body(null).build(), HttpStatus.OK);
    }
    //5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id , @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult){
        // 추후에 AOP 처리하면 좋은 로직임
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for (FieldError fe:
                    bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(),fe.getDefaultMessage());
            }
            throw  new RuntimeException(errorMap.toString());
        }
        BookRespDto bookRespDto = bookService.책수정하기(id, bookSaveReqDto);
        return new ResponseEntity<>(CMRRespDto.builder().code(1).msg("책 수정하기 성공").body(bookRespDto).build(), HttpStatus.OK);
    }
}
