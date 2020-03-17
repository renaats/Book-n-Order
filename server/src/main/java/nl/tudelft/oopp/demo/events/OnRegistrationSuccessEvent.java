package nl.tudelft.oopp.demo.events;

import nl.tudelft.oopp.demo.entities.AppUser;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationSuccessEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String appUrl;
    private AppUser user;

    public OnRegistrationSuccessEvent(AppUser user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}