package br.com.clinic.security;

import br.com.clinic.repositories.DoctorRepository;
import br.com.clinic.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final TokenService tokenService;
    private final DoctorRepository doctorRepository;

    @Autowired
    public SecurityConfiguration(TokenService tokenService, DoctorRepository doctorRepository) {
        this.tokenService = tokenService;
        this.doctorRepository = doctorRepository;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> {
                    auth.antMatchers(HttpMethod.POST, "/auth/login").permitAll();

                    auth.antMatchers(HttpMethod.GET).permitAll();
                    auth.antMatchers(HttpMethod.POST).permitAll();
                    auth.antMatchers(HttpMethod.PUT).permitAll();
                    auth.antMatchers(HttpMethod.DELETE).permitAll();

                    auth.anyRequest().authenticated();
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutheticationTokenFilter(tokenService, doctorRepository),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
