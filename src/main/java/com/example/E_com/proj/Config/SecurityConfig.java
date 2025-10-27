package com.example.E_com.proj.Config;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    //To load username and password for a respective user from DB to authenticate
    public UserDetailsService userDetailsService(){
        return new UserInfoUserDetailsService();

    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/products").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/products").authenticated().and()
                .formLogin()
                .and().build();
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(auth)



    }

//    private AuthenticationProvider authenticationProvider() {
//
//    }

}
