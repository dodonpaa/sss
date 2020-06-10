// for email validation
package com.vogella.javaweb.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import org.springframework.http.HttpStatus;


/*
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        httpServletResponse.getOutputStream().println(String.format(jsonPayload, e.getMessage(), Calendar.getInstance().getTime()));
    }
}
*/


@Component("authenticationFailureHandler2")
public class VendorAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageSource messages;

    //@Autowired
    //private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/vendors?error");
                
        super.onAuthenticationFailure(request, response, exception);
        
        //final Locale locale = localeResolver.resolveLocale(request);

        String errorMessage = "Invalid Credentials";//messages.getMessage("message.badCredentials", null, locale);

        if (exception.getMessage()
            .equalsIgnoreCase("User is disabled")) {
            errorMessage = "Your account is disabled please check your mail and click on the confirmation link";//messages.getMessage("auth.message.disabled", null, locale);
        } else if (exception.getMessage()
            .equalsIgnoreCase("User account has expired")) {
            errorMessage = "Your registration token has expired. Please register again";//messages.getMessage("auth.message.expired", null, locale);
        } else if (exception.getMessage()
            .equalsIgnoreCase("blocked")) {
            errorMessage = "This ip is blocked for 24 hours";//messages.getMessage("auth.message.blocked", null, locale);
        } else if (exception.getMessage()
            .equalsIgnoreCase("unusual location")) {
            errorMessage = "You are trying to login from unusual location, check your email for more details";//messages.getMessage("auth.message.unusual.location", null, locale);
        }

        request.getSession()
            .setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        
    }
}