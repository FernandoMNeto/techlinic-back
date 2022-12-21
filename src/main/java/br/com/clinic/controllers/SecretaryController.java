package br.com.clinic.controllers;

import br.com.clinic.api.in.SecretaryForm;
import br.com.clinic.api.out.SecretaryDTO;
import br.com.clinic.services.SecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secretaries")
public class SecretaryController {

    private final SecretaryService secretaryService;

    @Autowired
    public SecretaryController(SecretaryService secretaryService) {
        this.secretaryService = secretaryService;
    }

    @GetMapping
    public ResponseEntity<List<SecretaryDTO>> allSecretaries() {
        return secretaryService.allSecretaries();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SecretaryDTO> secretaryById(@PathVariable Long id) {
        return secretaryService.secretaryById(id);
    }

    @PostMapping(path = "/register")
    @Transactional
    public ResponseEntity<SecretaryDTO> registerSecretary(@RequestBody @Valid SecretaryForm secretaryForm,
                                                          UriComponentsBuilder uriComponentsBuilder) {
        return secretaryService.registerSecretary(secretaryForm, uriComponentsBuilder);
    }

    @PutMapping(path = "/update/{id}")
    @Transactional
    public ResponseEntity<SecretaryDTO> updateSecretary(@RequestBody @Valid SecretaryForm secretaryForm, @PathVariable Long id) {
        return secretaryService.updateSecretary(secretaryForm, id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteSecretary(@PathVariable Long id) {
        return secretaryService.deleteSecretary(id);
    }

}
