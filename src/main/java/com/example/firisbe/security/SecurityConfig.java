package com.example.firisbe.security;


import com.example.firisbe.util.error.ErrorCodes;
import com.example.firisbe.authentication.repository.UserRepository;
import com.example.firisbe.util.exception.FirisbeException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityFilter securityFilter;

    private final UserRepository userRepository;


    public SecurityConfig(SecurityFilter securityFilter, UserRepository userRepository) {
        this.securityFilter = securityFilter;
        this.userRepository = userRepository;
    }

    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository.findByUsername(username).orElseThrow(() -> new FirisbeException(ErrorCodes.ACCESS_DENIED)));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Details omitted for brevity

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        // Set unauthorized requests exception handler
        http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {response.sendError(HttpServletResponse.SC_UNAUTHORIZED,ex.getMessage());}).and();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/auth/token").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/auth/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/auth/users").permitAll()
                .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}