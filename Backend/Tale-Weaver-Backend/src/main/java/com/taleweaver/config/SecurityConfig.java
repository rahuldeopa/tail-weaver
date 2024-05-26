package com.taleweaver.config;

import com.taleweaver.jwt.JwtTokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception
    {
        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        Authroize -> Authroize.requestMatchers("/api/**").authenticated()

                                .anyRequest().permitAll()


                )
                .addFilterBefore(new JwtTokenValidator() , BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable()) .cors(cors->cors.configurationSource(configurationSource()))

                .formLogin(formLogin->formLogin.permitAll());



        return http.build();
    }

    private CorsConfigurationSource configurationSource()
    {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration cfg = new CorsConfiguration();

                cfg.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","OPTIONS", "DELETE"));  // Ensure all needed methods are allowed
                cfg.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));  // Allow necessary headers
                cfg.setAllowCredentials(true);  // Allow cookies, authorization, etc.
                return cfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
