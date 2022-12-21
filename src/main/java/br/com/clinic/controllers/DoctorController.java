package br.com.clinic.controllers;

import br.com.clinic.api.in.DoctorForm;
import br.com.clinic.api.out.DoctorDTO;
import br.com.clinic.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> allDoctors() {
        return doctorService.allDoctors();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DoctorDTO> doctorById(@PathVariable Long id) {
        return doctorService.doctorById(id);
    }

    @PostMapping(path = "/register")
    @Transactional
    public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody @Valid DoctorForm doctorForm,
                                                    UriComponentsBuilder uriBuilder) {
        return doctorService.registerDoctor(doctorForm, uriBuilder);
    }

    @PutMapping(path = "/update/{id}")
    @Transactional
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody @Valid DoctorForm doctorForm, @PathVariable Long id) {
        return doctorService.updateDoctor(doctorForm, id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        return doctorService.deleteDoctor(id);
    }

}
