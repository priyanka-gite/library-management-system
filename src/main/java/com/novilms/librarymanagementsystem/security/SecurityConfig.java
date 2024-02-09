package com.novilms.librarymanagementsystem.security;

import com.novilms.librarymanagementsystem.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // defining the authorization rules below per entity //

                        // for authentication //
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        // for user
                                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/{email}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasAnyRole("ADMIN")

                        // for author //
                                .requestMatchers(HttpMethod.PUT,"/author/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/author").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/author/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/author/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/author").hasAnyRole("ADMIN","USER")

                        // for Books
                                .requestMatchers(HttpMethod.GET, "books").permitAll()
                                .requestMatchers(HttpMethod.GET, "books/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "books").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "books/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "books/{id}").hasRole("ADMIN")

                        // for file upload/download
                                .requestMatchers(HttpMethod.POST, "single/uploadDb").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/downloadFromDB/{fileName}").hasAnyRole("ADMIN","USER")

                        // for reservation //
                                .requestMatchers(HttpMethod.GET, "reservations").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "reservations/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "reservations").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "reservations/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "reservations/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "reservations/return-book/{id}").hasAnyRole("ADMIN", "USER")

                        // for roles //
                                .requestMatchers(HttpMethod.GET, "/roles").hasRole("ADMIN")

                        // for subscriptions //
                                .requestMatchers(HttpMethod.GET, "/subscriptions/{id}").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.GET, "/subscriptions").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/subscriptions/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/subscriptions").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.PUT, "/subscriptions/{id}").hasAnyRole("ADMIN","USER")
                        //.anyRequest().permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(userDetailsService(),jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
