package br.com.clinic.repositories;

import br.com.clinic.entities.models.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, Long> {
    List<Pacient> findByFirstNameStartsWithIgnoreCase(String name);

    Optional<Pacient> findByCpf(String cpf);
}
