package br.com.clinic.controllers;

import br.com.clinic.api.in.LoginForm;
import br.com.clinic.api.out.TokenDTO;
import br.com.clinic.services.TokenService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginForm loginForm) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUsername(),
                            loginForm.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("bad credentials");
        }

        return new ResponseEntity<>(new TokenDTO(tokenService.generateToken(authentication), "Bearer"), HttpStatus.OK);
    }

    @GetMapping("/isTokenValid")
    public ResponseEntity<Void> isTokenValid() {
        return ResponseEntity.ok().build();
    }

}
