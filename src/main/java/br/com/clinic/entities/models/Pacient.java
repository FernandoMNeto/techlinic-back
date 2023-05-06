package br.com.clinic.entities.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import br.com.clinic.api.in.PacientForm;
import br.com.clinic.entities.abstracts.People;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pacient")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pacient extends People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacient")
    private List<Consult> consults;

    public Pacient(String firstName, String lastName, String cpf, LocalDate bornAt,
                   Contact contact, Address address) {
        super(firstName, lastName, cpf, bornAt, contact, address);
        this.consults = new ArrayList<>();
    }

    public Pacient(PacientForm pacientForm) {
        super(pacientForm.getFirstName(), pacientForm.getLastName(), pacientForm.getCpf(), pacientForm.getBornAt(),
                pacientForm.getContact(), pacientForm.getAddress());
        this.consults = new ArrayList<>();
    }

    public void addNewConsult(Consult consult) {
        this.consults.add(consult);
    }

}
