package ru.hse.smart_pro.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.hse.smart_pro.data.service.UserService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoderConfig passwordEncoderConfig;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.formLogin(form -> form
                .loginPage("/login").defaultSuccessUrl("/", true))
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/cv/view").permitAll()
                .requestMatchers("/cv").authenticated()
                .requestMatchers("/").authenticated()
                .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .logout((logout) -> logout.logoutSuccessUrl("/login")).build();
    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoderConfig.getPasswordEncoder());
    }
}