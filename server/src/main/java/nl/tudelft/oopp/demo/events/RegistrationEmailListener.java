package nl.tudelft.oopp.demo.events;

import java.util.Random;
import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) {
        Random random = new Random();
        int sixDigitNumber = random.nextInt(999999);
        String stringNumber = String.format("%06d", sixDigitNumber);
        AppUser user = userRepository.findByEmail(event.getUser().getEmail());
        user.setConfirmationNumber(Integer.parseInt(stringNumber));
        userRepository.save(user);
        String recipient = user.getEmail();
        String subject = "Registration Confirmation";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText("This is the confirmation email message.\nPlease enter the six digits into the field in order to confirm this account.\n"
                + "Six-digit-code:    " + stringNumber);
        mailSender.send(email);
    }
}

