package nl.tudelft.oopp.demo.events;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

    @Component
    public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
        @Autowired
        private UserService userService;
        @Qualifier("messageSource")
        @Autowired
        private MessageSource messages;
        @Autowired
        private MailSender mailSender;

        @Override
        public void onApplicationEvent(OnRegistrationSuccessEvent event) {
            this.confirmRegistration(event);
        }
        private void confirmRegistration(OnRegistrationSuccessEvent event) {
            AppUser user = event.getUser();
            String token = UUID.randomUUID().toString();
            userService.createVerificationToken(user,token);

            String recipient = user.getEmail();
            String subject = "Registration Confirmation";
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipient);
            email.setSubject(subject);
            email.setText("This is the confirmation email message.\nPlease enter the token into the field in order to confirm this account.\n" + "Token:    " + token);
            mailSender.send(email);

        }
    }

