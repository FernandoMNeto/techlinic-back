package br.com.clinic.security.filter;

import br.com.clinic.entities.models.UserInfo;
import br.com.clinic.repositories.DoctorRepository;
import br.com.clinic.repositories.UserInfoRepository;
import br.com.clinic.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutheticationTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public AutheticationTokenFilter(TokenService tokenService, UserInfoRepository userInfoRepository) {
        this.tokenService = tokenService;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);
        boolean tokenIsValid = tokenService.isTokenValid(token);

        if (tokenIsValid) {
            autheticateToken(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autheticateToken(String token) {

        String username = tokenService.getUsernameToken(token);

        UserInfo userInfo = userInfoRepository.findByUsername(username);

        Authentication authetication = new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword(), userInfo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authetication);
    }

    private String recoverToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }
}
