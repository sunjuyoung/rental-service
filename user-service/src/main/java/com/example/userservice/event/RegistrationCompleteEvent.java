package com.example.userservice.event;

import com.example.userservice.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter @Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private AppUser appUser;
    private String url;

    public RegistrationCompleteEvent(AppUser appUser,String url) {
        super(appUser);
        this.appUser = appUser;
        this.url = url;
    }
}
