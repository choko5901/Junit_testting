package com.example.JunitExercize.util;

import org.springframework.stereotype.Component;

@Component
public class MailSenderAdapter implements MailSender {
    private Mail mail;

    public MailSenderAdapter(){
        this.mail = new Mail();
    }


    @Override
    public boolean send(){
        return mail.sendMail();
    }
//    @Override
//    public boolean send(){
//        return true;
//    }

// Mail이 가직 구현되어 있지 않으면 어뎁터는 추후에 사용한다 private Mail mail; 여서 부터 바로 에러 터져버림
//
}
