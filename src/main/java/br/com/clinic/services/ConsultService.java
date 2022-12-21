package br.com.clinic.services;

import br.com.clinic.api.in.ConsultForm;
import br.com.clinic.api.out.ConsultDTO;
import br.com.clinic.entities.models.Hours;
import br.com.clinic.entities.models.Consult;
import br.com.clinic.entities.models.Doctor;
import br.com.clinic.entities.models.Pacient;
import br.com.clinic.error.resourcenotfound.ResourceNotFoundException;
import br.com.clinic.repositories.ConsultRepository;
import br.com.clinic.repositories.DoctorRepository;
import br.com.clinic.repositories.PacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConsultService {

    private final ConsultRepository consultRepository;
    private final PacientRepository pacientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ConsultService(ConsultRepository consultRepository, PacientRepository pacientRepository, DoctorRepository doctorRepository) {
        this.consultRepository = consultRepository;
        this.pacientRepository = pacientRepository;
        this.doctorRepository = doctorRepository;
    }

    public ResponseEntity<List<ConsultDTO>> dailyConsults() {
        List<Consult> dailyConsults = consultRepository.findByDate(LocalDate.now());
        return ResponseEntity.ok(ConsultDTO.convertConsultToDTO(dailyConsults));
    }


    public ResponseEntity<List<ConsultDTO>> doctorDailyConsults(Long id) {

        Optional<Doctor> oDoctor = doctorRepository.findById(id);

        if(oDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }

        List<Consult> doctorDailyConsults = consultRepository.findByDoctorAndDate(oDoctor.get(), LocalDate.now());

        return ResponseEntity.ok(ConsultDTO.convertConsultToDTO(doctorDailyConsults));
    }

    public ResponseEntity<List<LocalTime>> doctorAvailability(Long id, String date) {

        Optional<Doctor> oDoctor = doctorRepository.findById(id);
        List<LocalTime> availableHours = new Hours().getHours();

        if (oDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }

        List<Consult> dateConsults = consultRepository.findByDoctorAndDate(oDoctor.get(), LocalDate.parse(date));

        for (Consult c : dateConsults) {
            availableHours.remove(c.getTime());
        }

        return ResponseEntity.ok(availableHours);
    }

    public ResponseEntity<ConsultDTO> registerConsult(ConsultForm consultForm) {

        Optional<Pacient> oPacient = pacientRepository.findById(consultForm.getPacientId());
        Optional<Doctor> oDoctor = doctorRepository.findById(consultForm.getDoctorId());

        if (oPacient.isEmpty()) {
            throw new ResourceNotFoundException("Pacient not found with ID: " + consultForm.getPacientId());
        }else if (oDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + consultForm.getDoctorId());
        }

        Pacient pacient = oPacient.get();
        Doctor doctor = oDoctor.get();

        Consult consult = new Consult(consultForm, pacient, doctor);

        pacient.addNewConsult(consult);
        doctor.addNewConsult(consult);

        consultRepository.save(consult);
        pacientRepository.save(pacient);
        doctorRepository.save(doctor);

        return ResponseEntity.ok(new ConsultDTO(consult));
    }


}
