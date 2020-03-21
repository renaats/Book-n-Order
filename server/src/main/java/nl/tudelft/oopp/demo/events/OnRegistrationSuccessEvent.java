package nl.tudelft.oopp.demo.events;

import java.util.Locale;
import nl.tudelft.oopp.demo.entities.AppUser;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationSuccessEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private AppUser user;

    /**
     * The constructor of the event class.
     * @param user The registered user
     */
    public OnRegistrationSuccessEvent(AppUser user) {
        super(user);
        this.user = user;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}