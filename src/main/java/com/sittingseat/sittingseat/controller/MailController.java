package com.sittingseat.sittingseat.controller;

import com.sittingseat.sittingseat.dto.MailAuthenticationDto;
import com.sittingseat.sittingseat.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail/authentication")
    public ResponseEntity<String> mailAuth(@RequestBody MailAuthenticationDto mailAuthenticationDto) throws MessagingException, UnsupportedEncodingException {
        String authCode = mailService.sendEmail(mailAuthenticationDto.getEmail());
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }
}
