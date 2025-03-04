package com.makar.tenant.security.config;

import com.makar.tenant.security.JwtAuthorizationFilter;
import com.makar.tenant.security.UsernamePasswordRoleAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] EXCLUDED_URLS = new String[]{"/actuator/**",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-ui"};

    private static final String[] AUTH_URLS = new String[]{"/admins/auth/login", "/admins/auth/refresh", "/users/auth/refresh",
            "/users/auth/login"};

    private static final String[] ADMIN_URLS = new String[]{"/admins/**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            JwtAuthorizationFilter jwtAuthorizationFilter,
                                            UsernamePasswordRoleAuthenticationProvider authenticationProvider) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(EXCLUDED_URLS).permitAll()
                        .requestMatchers(AUTH_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(HttpBasicConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(List.of(ALL));
        corsConfig.setAllowedMethods(List.of(ALL));
        corsConfig.setAllowedHeaders(List.of(ALL));
        corsConfig.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
