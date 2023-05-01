package com.sittingseat.sittingseat.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @InjectMocks
    private MailService mailService;

    @Test
    void sendEmail() throws MessagingException, UnsupportedEncodingException {
        // given
        String email = "suudh99@naver.com";
        String expectedCode = "QWEASD123";

        MimeMessage mimeMessage = new MimeMessage((Session) null); // 유효한 MimeMessage 객체 생성
        doReturn(mimeMessage).when(mailSender).createMimeMessage(); // createMimeMessage() 호출 시 미리 생성한 객체 반환

        doNothing().when(mailSender).send(any(MimeMessage.class));
        when(mailService.sendEmail(email)).thenReturn(expectedCode);

//        doNothing().when(templateEngine).process(anyString(), any(Context.class));
//        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<h1></h1>");

        // when
        String authCode = mailService.sendEmail(email);

        // then
        Assertions.assertThat(authCode).isEqualTo(expectedCode);
    }
}