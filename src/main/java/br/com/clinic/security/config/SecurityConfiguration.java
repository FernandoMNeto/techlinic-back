package br.com.clinic.security.config;


import br.com.clinic.security.filter.AutheticationTokenFilter;
import br.com.clinic.repositories.UserInfoRepository;
import br.com.clinic.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final TokenService tokenService;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public SecurityConfiguration(TokenService tokenService, UserInfoRepository userInfoRepository) {
        this.tokenService = tokenService;
        this.userInfoRepository = userInfoRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests(auth -> {
                    auth.antMatchers(HttpMethod.POST, "/login").permitAll();
                    auth.antMatchers("/swagger-ui.html").permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(new AutheticationTokenFilter(tokenService, userInfoRepository), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
