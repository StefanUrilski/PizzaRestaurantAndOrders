package com.pizzaapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import com.pizzaapp.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public ApplicationSecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .disable()
                .csrf()
//                    .csrfTokenRepository(this.csrfTokenRepository())
                    .disable()
//                .and()
                .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and()
                .rememberMe()
                    .rememberMeParameter("rememberMe")
                    .key("rmmbrm")
                    .userDetailsService(this.userService)
                    .rememberMeCookieName("RMMBRM")
                    .tokenValiditySeconds(1200)
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error/unauthorized")
                ;
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");

        return repository;
    }
}
