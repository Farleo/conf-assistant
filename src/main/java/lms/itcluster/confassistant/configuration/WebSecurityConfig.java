package lms.itcluster.confassistant.configuration;

import lms.itcluster.confassistant.model.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/get-all-questions/**", "/like/**", "/save-question/**").hasAnyAuthority("Admin", "Guest", "Moderator", "Speaker")
                .anyRequest().permitAll();
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login-handler")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) ->
                        response.sendError(HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()))
                .failureHandler((request, response, exception) -> {
                    if (request.getParameter("password").equals("")) {
                        response.sendError(HttpStatus.UNAUTHORIZED.value(),
                                HttpStatus.UNAUTHORIZED.getReasonPhrase());
                    }
                    else {
                        response.sendError(HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase());
                    }
                });
        http.logout()
                .logoutUrl("/log-out")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

}