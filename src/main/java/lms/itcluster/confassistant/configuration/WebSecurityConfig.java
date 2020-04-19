package lms.itcluster.confassistant.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/like/**", "/save-question/**").hasAnyRole("ADMIN", "CONFOWNER", "USER")
                .antMatchers( "/register/conference/**").authenticated()
/*                .antMatchers( "/moderator/**").hasRole("USER")
                .antMatchers( "/moderator/**").hasAuthority("moder")*/
                .antMatchers( "/edit/speaker/**").hasAuthority("speaker")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/conf/owner/**").hasAnyRole("ADMIN", "CONFOWNER")
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
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        /*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123qweasd");
        assert passwordEncoder.matches("$2a$10$6beagrBPfIHPRfYzVPQw.OFQ6AiBlW9oab3vE0u8D7BZh9JwyVoMi", password);
        System.out.println(password);*/
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("");
        System.out.println(passwordEncoder.matches("", password));
        System.out.println(password);
    }

}
