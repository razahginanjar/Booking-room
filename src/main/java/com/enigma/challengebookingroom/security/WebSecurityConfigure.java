package com.enigma.challengebookingroom.security;

import com.enigma.challengebookingroom.constant.APIUrl;
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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
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
                                    .requestMatchers(APIUrl.RESERVATION + APIUrl.PATH_STATUS + APIUrl.PATH_VAR_ID).permitAll()
                                    .requestMatchers(APIUrl.RESERVATION + APIUrl.SUCCESS).permitAll()
                                    .requestMatchers(APIUrl.RESERVATION + APIUrl.ALREADY_CLICK).permitAll()
                                    .requestMatchers(
                                            "/v2/api-docs",
                                            "/v3/api-docs",
                                            "v3/**",
                                            "/v3/api-docs/**",
                                            "/swagger-resources",
                                            "/swagger-resources/**",
                                            "/configuration/ui",
                                            "/configuration/security",
                                            "/swagger-ui/**",
                                            "/webjars/**",
                                            "/swagger-ui.html")
                                    .permitAll()
                                    //.requestMatchers("/**").permitAll() //for test
                                    .anyRequest().authenticated();
                        }
                )

                .addFilterBefore(filter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
