package com.enigma.challengebookingroom.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
//SETTING AUTHORIZATION
@EnableWebSecurity

@EnableMethodSecurity
public class WebSecurityConfigure {

    private final AuthenticationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    );
                })
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> {
                            authorizationManagerRequestMatcherRegistry
                                    .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                    .requestMatchers("/api/v1/auth/**").permitAll()
                                    .requestMatchers("/api/**").permitAll() //bisa dipakai untuk test kalo semisal ada permit test controller harus login
                                    .anyRequest().authenticated();
                        }
                )

                .addFilterBefore(filter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
