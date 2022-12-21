package br.com.clinic.entities.abstracts;

import br.com.clinic.entities.models.Address;
import br.com.clinic.entities.models.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class People {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String cpf;
    private LocalDate bornAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public People(String firstName, String lastName, String cpf, LocalDate bornAt, 
            Contact contact, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.bornAt = bornAt;
        this.contact = contact;
        this.address = address;
    }

}
