package com.ChatApp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityFilter {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (requests)->{
                            requests.requestMatchers(HttpMethod.GET,"/login","/register").permitAll()
                                    .requestMatchers(HttpMethod.POST,"/register","/login").permitAll()
                                    .anyRequest().authenticated();


                        }
                )
        ;

        return http.build();
    }
}
