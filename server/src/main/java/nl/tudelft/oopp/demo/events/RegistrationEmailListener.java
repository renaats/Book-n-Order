package nl.tudelft.oopp.demo.events;

import nl.tudelft.oopp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class RegistrationEmailListener {
    @Component
    public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
        @Autowired
        private UserService userService;
        @Autowired
        private MailSender mailSender;

        @Override
        public void onApplicationEvent(OnRegistrationSuccessEvent event) {
            this.confirmRegistration(event);
        }

        private void confirmRegistration(OnRegistrationSuccessEvent event) {

        }
    }
}
