package br.com.clinic.repositories;

import br.com.clinic.entities.models.Consult;
import br.com.clinic.entities.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
    List<Consult> findByDate(LocalDate date);
    List<Consult> findByDoctorAndDate(Doctor doctor, LocalDate date);


}
