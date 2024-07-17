package org.pronet.shoppie.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.repositories.UserRepository;
import org.pronet.shoppie.services.UserService;
import org.pronet.shoppie.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String email = request.getParameter("username");
        UserEntity foundedUser = userRepository.findByEmail(email);
        if (foundedUser != null) {
            if (foundedUser.getIsEnabled()) {
                if (foundedUser.getAccountNonLocked()) {
                    if (foundedUser.getFailedAttempt() < AppConstants.ATTEMPT_COUNT) {
                        userService.increaseFailedAttempt(foundedUser);
                    } else {
                        userService.userAccountLock(foundedUser);
                        exception = new LockedException("Your account is locked! You have 3 failed attempt!");
                    }
                } else {
                    if (userService.unlockAccountTimeExpired(foundedUser)) {
                        exception = new LockedException("Your account is unlocked! Please try to login!");
                    } else {
                        exception = new LockedException("Your account is locked! Please try after sometimes!");
                    }
                }
            } else {
                exception = new LockedException("Your account is inactive!");
            }
        } else {
            exception = new LockedException("Email is not exist!");
        }
        super.setDefaultFailureUrl("/sign-in?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
