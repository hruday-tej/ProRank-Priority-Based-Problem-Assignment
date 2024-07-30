package dev.hruday.ProRank.Security.event.listener;

import dev.hruday.ProRank.Security.entity.User;
import dev.hruday.ProRank.Security.event.RegistrationCompleteEvent;
import dev.hruday.ProRank.Security.service.UserServericeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserServericeImpl userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
//        create verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
//        send mail to user

    }
}
