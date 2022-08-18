package com.example.userservice.event;

import com.example.userservice.config.AppProperties;
import com.example.userservice.entity.AppUser;
import com.example.userservice.mail.EmailMessage;
import com.example.userservice.mail.EmailService;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        AppUser appUser = event.getAppUser();
        String url = event.getUrl();
        //send email;
        Context context = new Context();
        String link =  url+"?token="+appUser.getToken()+"&email="+appUser.getEmail();
        context.setVariable("link",link);
        context.setVariable("nickname",appUser.getNickname());
        context.setVariable("linkName","이메일 인증하기");
        context.setVariable("message","서비스를 사용하려면 링크를 클릭하세요");
        context.setVariable("host",appProperties.getHost());

        String message = templateEngine.process("mail/link",context);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(appUser.getEmail())
                .subject("회원 가입 인증")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);

    }
}
