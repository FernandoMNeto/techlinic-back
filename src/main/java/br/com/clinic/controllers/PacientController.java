package br.com.clinic.controllers;

import br.com.clinic.api.in.PacientForm;
import br.com.clinic.api.out.PacientDTO;
import br.com.clinic.services.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/pacients")
public class PacientController {

    private final PacientService pacientService;

    @Autowired
    public PacientController(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    @GetMapping
    public ResponseEntity<List<PacientDTO>> allPacients() {
        return pacientService.allPacients();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PacientDTO> pacientById(@PathVariable Long id) {
        return pacientService.pacientById(id);
    }
    /*
    @PostMapping(path = "/consults/add")
    public ResponseEntity<PacientDTO> registerConsultForPacient(ConsultForm consultForm, Long id) {

    }
    */
    @PostMapping(path = "/register")
    @Transactional
    public ResponseEntity<PacientDTO> registerPacient(@RequestBody @Valid PacientForm pacientForm, UriComponentsBuilder uriBuilder) {
        return pacientService.registerPacient(pacientForm, uriBuilder);
    }

    @PutMapping(path = "/update/{id}")
    @Transactional
    public ResponseEntity<PacientDTO> updatePacient(@RequestBody @Valid PacientForm pacientForm, @PathVariable Long id) {
        return pacientService.updatePacient(pacientForm, id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity<Void> deletePacient(@PathVariable Long id) {
        return pacientService.deletePacient(id);
    }

}
