package com.example.E_com.proj.Config;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    //To load username and password for a respective user from DB to authenticate
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        return new UserInfoUserDetailsService();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/products").permitAll()
                        .requestMatchers("/api/product/{id}").authenticated()
                )
                .formLogin(form -> form.permitAll()); // enables default login form

        return http.build();
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(auth)



    }


}
// When a user attempts to log in, Spring Security uses the configured PasswordEncoder to encode the provided password and then
// compares it with the stored encoded password associated with the user. If they match, authentication is successful

