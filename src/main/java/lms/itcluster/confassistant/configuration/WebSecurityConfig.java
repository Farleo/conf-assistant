package lms.itcluster.confassistant.configuration;

import lms.itcluster.confassistant.model.Constant;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/like/**", "/save-question/**", "/get-all-questions/", "/register/conference/**", "/change/email/**").authenticated()
                .antMatchers("/moderator").hasRole(Constant.ROLE_USER)
                .antMatchers("/moderator").hasAuthority(Constant.MODERATOR)
                .antMatchers("/speaker").hasRole(Constant.ROLE_USER)
                .antMatchers("/speaker").hasAuthority(Constant.SPEAKER)
                .antMatchers( "/manage/topic/**").hasAnyRole(Constant.ROLE_USER, Constant.ROLE_ADMIN, Constant.ROLE_CONFOWNER)
                .antMatchers( "/manage/topic/**").hasAnyAuthority(Constant.MODERATOR, Constant.ADMIN, Constant.ROLE_CONFOWNER)
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/conf/owner/**").hasAnyRole(Constant.ROLE_ADMIN, Constant.ROLE_CONFOWNER)
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
                    } else {
                        response.sendError(HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase());
                    }
                });
        http.logout()
                .logoutUrl("/log-out")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.rememberMe()
                .rememberMeParameter("remember-me")
                .key("conf-assistant")
                .tokenRepository(persistentTokenRepository());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
