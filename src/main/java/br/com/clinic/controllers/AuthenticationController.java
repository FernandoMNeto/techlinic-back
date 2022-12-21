package br.com.clinic.controllers;

import br.com.clinic.api.in.LoginForm;
import br.com.clinic.api.out.TokenDTO;
import br.com.clinic.services.DoctorService;
import br.com.clinic.services.SecretaryService;
import br.com.clinic.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    private final DoctorService doctorService;
    private final SecretaryService secretaryService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, DoctorService doctorService, SecretaryService secretaryService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.doctorService = doctorService;
        this.secretaryService = secretaryService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getUsername(),
                        loginForm.getPassword()
                )
        );
        return new ResponseEntity<>(new TokenDTO(tokenService.generateToken(authentication), "Bearer"), HttpStatus.OK);
    }
}
