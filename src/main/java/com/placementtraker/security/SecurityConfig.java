
package com.placementtraker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            // Enable CORS
            .cors(Customizer.withDefaults())

            // Disable CSRF for stateless JWT APIs
            .csrf(csrf -> csrf.disable())

            // Don't create HTTP sessions
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )


            .authorizeHttpRequests(auth -> auth

            		// Allow CORS preflight requests
            		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            		// Public registration and login
            		.requestMatchers(HttpMethod.POST,
            				"/api/register",
            				"/api/login"
            				).permitAll()

            		// Admin-only endpoints
            		.requestMatchers("/api/admin/**").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.GET, "/api/students").hasRole("ADMIN")

            		// Student management endpoints: admin only for now
            		.requestMatchers("/api/students/**").hasRole("STUDENT")
            		

            		// Everything else requires authentication
            		.anyRequest().authenticated()
            		)


            // Add JWT filter before username/password authentication
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // AuthenticationManager for login authentication
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}
