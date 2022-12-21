package br.com.clinic.api.in;

import br.com.clinic.entities.models.Contact;
import br.com.clinic.entities.models.Address;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
public class PacientForm {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @CPF
    private String cpf;
    @Past
    private LocalDate bornAt;
    @Valid
    private Contact contact;
    @Valid
    private Address address;

}
