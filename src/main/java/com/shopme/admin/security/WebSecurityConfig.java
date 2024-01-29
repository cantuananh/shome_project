package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    UserDetailsService userDetailsService() {
        return new ShopmeUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/shopme/admin/home").hasAnyAuthority("Admin", "Salesperson", "Editor", "Shipper" , "Assistant")
                        .requestMatchers("/shopme/admin/users/**").hasAuthority("Admin")
                        .requestMatchers("/shopme/admin/categories/**").hasAnyAuthority("Admin", "Editor")
                        .requestMatchers("/shopme/admin/brands/**").hasAnyAuthority("Admin", "Editor")
                        .requestMatchers("/shopme/admin/products/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                        .requestMatchers("/shopme/admin/customers/**").hasAnyAuthority("Admin", "Salesperson")
                        .requestMatchers("/shopme/admin/shipping/**").hasAnyAuthority("Admin", "Salesperson")
                        .requestMatchers("/shopme/admin/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
                        .requestMatchers("/shopme/admin/reports/**").hasAnyAuthority("Admin", "Salesperson")
                        .requestMatchers("/shopme/admin/articles/**").hasAnyAuthority("Admin", "Editor")
                        .requestMatchers("/shopme/admin/menus/**").hasAnyAuthority("Admin", "Editor")
                        .requestMatchers("/shopme/admin/settings/**").hasAnyAuthority("Admin")
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form
                        .loginPage("/shopme/admin/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/shopme/admin/home")
                        .permitAll()
                ).logout(logout -> logout.permitAll());

        http.headers(headers -> headers.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }

    @Bean
    WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
