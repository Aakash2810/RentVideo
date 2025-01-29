package com.crio.rentvideo.config;


import java.net.Authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.crio.rentvideo.service.UserService;
import com.crio.rentvideo.service.impl.UserServiceImpl;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserServiceImpl userService;
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 
        // http.csrf(crsf->crsf.disable());
        // http.authenticationProvider(authenticationProvider());
        // http.authorizeHttpRequests(conf-> conf.requestMatchers("/api/auth/register", "/api/auth/login").permitAll().anyRequest().authenticated());
        
        // http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable())  // Disabling CSRF if not necessary
            .authenticationProvider(authenticationProvider())  // Configure custom authentication provider
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()  // Permit these requests
                    .requestMatchers("/api/videos/**").hasRole("ADMIN")  // Admin only can view videos
                    .anyRequest().authenticated()  // All other endpoints require authentication
            )
            .httpBasic(Customizer.withDefaults());  // Basic authentication, customizing the default settings


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception{
        return conf.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider (){
        return new CustomAuthenticationProvider(userService, passwordEncoder());
    }
    @Bean
    PasswordEncoder passEncoder(){
        return new BCryptPasswordEncoder();
    }

}
