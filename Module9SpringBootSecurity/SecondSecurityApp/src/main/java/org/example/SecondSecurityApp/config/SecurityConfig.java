package org.example.SecondSecurityApp.config;

import org.example.SecondSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/admin").hasRole("ADMIN") //ограничение в кофниге
                    .requestMatchers("/auth/login", "/auth/registration","/error").permitAll()
                    .anyRequest().authenticated())
                .formLogin(form -> form
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/hello")
                    .failureUrl("/auth/login?error")
                    .permitAll())
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
