package br.com.clinic.repositories;

import br.com.clinic.entities.models.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, Long> {
}
