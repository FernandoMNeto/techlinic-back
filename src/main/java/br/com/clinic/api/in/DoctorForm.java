package br.com.clinic.api.in;

import br.com.clinic.entities.models.Contact;
import br.com.clinic.entities.models.Address;
import br.com.clinic.entities.models.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DoctorForm {
    @NotBlank
    private String crm;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String cpf;
    @Past
    private LocalDate bornAt;
    @Valid
    private UserInfo userInfo;
    @Valid
    private Contact contact;
    @Valid
    private Address address;
}
