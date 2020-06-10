// for email validation
package com.vogella.javaweb;

import java.util.Locale;

import com.vogella.javaweb.model.Customer;
import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Customer user;

    public OnRegistrationCompleteEvent(final Customer user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
    
   /* public OnRegistrationCompleteEvent(final Customer user,final String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }*/

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Customer getUser() {
        return user;
    }
}