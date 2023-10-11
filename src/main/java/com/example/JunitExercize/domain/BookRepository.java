package com.example.JunitExercize.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
    // 레파지토리이에 JPA 연결시 해당 JPA 엔터티와 해당 엔터티의 PK 타입을 적어주는듯
}
