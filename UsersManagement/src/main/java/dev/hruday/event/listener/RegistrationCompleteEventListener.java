package dev.hruday.event.listener;

import dev.hruday.entity.UserEntity;
import dev.hruday.event.RegistrationCompleteEvent;
import dev.hruday.service.UserServericeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserServericeImpl userService;

    @Autowired
    public RegistrationCompleteEventListener(UserServericeImpl userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
//        create verification token for the user with link
        UserEntity userEntity = event.getUserEntity();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, userEntity);
//        send mail to user
        String url = event.getApplicationUrl() + "/auth/verifyRegistration?token="+token;
        log.info("click the link to verify your account : {}", url);
    }
}
