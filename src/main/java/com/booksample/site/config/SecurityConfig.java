package com.booksample.site.config;

import com.booksample.site.filters.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final JWTFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer())
                .authorizeHttpRequests(request->
                    request
                            .requestMatchers(
                            "/api/v1/auth/**"
                                    , "/v2/api-docs"
                                    , "/v3/api-docs"
                                    , "/v3/api-docs/**"
                                    , "/swagger-resouces"
                                    , "/swagger-resouces/**"
                                    , "/configuration/security"
                                    , "/consiguration/ui"
                                    , "/configuration/security"
                                    , "/swagger-ui/**"
                                    , "/webjars/**"
                                    , "/swagger-ui.html"
                            )
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                )
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(customLogout());
        return httpSecurity.build();
    }
    private static Customizer<CsrfConfigurer<HttpSecurity>> CsrfConfigurer(){
        return csrf -> csrf.disable();
    }
    private static Customizer<LogoutConfigurer<HttpSecurity>> customLogout(){
        return logout -> logout
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler((request, response, authentication)->{
                    SecurityContextHolder.clearContext();
                })
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
}
