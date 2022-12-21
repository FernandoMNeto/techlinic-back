package br.com.clinic.controllers;

import br.com.clinic.api.in.ConsultForm;
import br.com.clinic.api.out.ConsultDTO;
import br.com.clinic.services.ConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/consults")
public class ConsultController {

    private final ConsultService consultService;

    @Autowired
    public ConsultController(ConsultService consultService) {
        this.consultService = consultService;
    }

    @GetMapping(path = "/daily")
    public ResponseEntity<List<ConsultDTO>> dailyConsults() {
        return consultService.dailyConsults();
    }

    @GetMapping(path = "/doctor/{id}")
    public ResponseEntity<List<ConsultDTO>> doctorDailyConsults(@PathVariable Long id) {
        return consultService.doctorDailyConsults(id);
    }

    @GetMapping(path = "/doctor/{id}/availability/{date}")
    public ResponseEntity<List<LocalTime>> doctorAvailability(@PathVariable Long id, @PathVariable String date) {
        return consultService.doctorAvailability(id, date);
    }

    @PostMapping(path = "/register")
    @Transactional
    public ResponseEntity<ConsultDTO> registerConsult(@RequestBody ConsultForm consultForm) {
        return consultService.registerConsult(consultForm);
    }

}
