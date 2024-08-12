package org.pronet.shoppie.configs;

import org.pronet.shoppie.services.impls.UserDetailsServiceImpl;
import org.pronet.shoppie.utils.RequestMatcherPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    @Lazy
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(RequestMatcherPatterns.NON_AUTH_MATCHERS)
                        .permitAll())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(RequestMatcherPatterns.USER_AUTH_MATCHERS)
                        .hasAuthority("User"))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(RequestMatcherPatterns.ADMIN_AUTH_MATCHERS)
                        .hasAuthority("Admin"))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(RequestMatcherPatterns.USER_AND_ADMIN_AUTH_MATCHERS)
                        .hasAnyAuthority("User", "Admin"))
                .formLogin(form -> form
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/login")
                        .failureHandler(authenticationFailureHandler)
                        .successHandler(authenticationSuccessHandler)
                )
                .logout(LogoutConfigurer::permitAll);
        return security.build();
    }


}
