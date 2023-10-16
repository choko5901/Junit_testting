package com.example.JunitExercize.web;

import com.example.JunitExercize.domain.Book;
import com.example.JunitExercize.domain.BookRepository;
import com.example.JunitExercize.service.BookService;
import com.example.JunitExercize.web.dto.request.BookSaveReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.IntegerAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;


//통합 테스트 (C,S,R)
// 컨트러만 테스트 하는 것이 아님
// 컨톨러 단 역시 따로 Unit 테스트 가능함 Mockito 사용해서
// 주로 배포시에 테스트 서버에서 통합 테스트 한번만 돌려본다
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private TestRestTemplate rt;
    @Autowired
    private BookRepository bookRepository;
    private static ObjectMapper om;
    private static HttpHeaders headers;
    @BeforeAll
    public static void init(){
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach // 각 테스트 메소드 시작전 매번 시행
    public void 데이터준비(){
        String title = "Junit 기초";
        String author = "더존도서";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }


    @Test
    public void  saveBook_test() throws Exception{
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("Junit 기초");
        bookSaveReqDto.setAuthor("더존 출판사");

        String body = om.writeValueAsString(bookSaveReqDto);


        // when
        HttpEntity<String> requset = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, requset, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        // Json 데이터를 원래 검증 하려면 Dto 등 Object 로 변환 해줘야하나
        // 그냥 문자열로 바로 연산자, 함수, 표현식 , 검증 등을 수행 할 수잇도록 제공 하는것이 JsonPath 이다
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo("Junit 기초");
        assertThat(author).isEqualTo("더존 출판사");
    }
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookList_test(){
        //given

        //when
        HttpEntity<String> requset = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, requset, String.class);
        System.out.println(response.getBody());
        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.items[0].title");
        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("Junit 기초");
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookOne_test(){
        //given
        Integer id = 1;
        //when
        HttpEntity<String> requset = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.GET, requset, String.class);
        System.out.println(response.getBody());
        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.title");
        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("Junit 기초");
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void deleteBook_test(){
        //given
        Integer id = 1;
        //when
        HttpEntity<String> requset = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.DELETE, requset, String.class);
        System.out.println(response.getBody());
        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        assertThat(code).isEqualTo(1);
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void updateBook_test() throws JsonProcessingException {
        //given
        Integer id =1;
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("spring");
        bookSaveReqDto.setAuthor("비즈온 출판사");

        String body = om.writeValueAsString(bookSaveReqDto);

        //when
        HttpEntity<String> requset = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.PUT, requset, String.class);
        System.out.println(response.getBody());
        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        assertThat(title).isEqualTo("spring");
    }


}
