package br.com.clinic.api.out;

import br.com.clinic.entities.models.Secretary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SecretaryDTO {

    private Long id;
    private String cpf;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Integer age;
    private LocalDate bornAt;
    private ContactDTO contact;
    private AddressDTO address;

    public SecretaryDTO(Secretary secretary) {
        this.id = secretary.getId();
        this.cpf = secretary.getCpf();
        this.firstName = secretary.getFirstName();
        this.lastName = secretary.getLastName();
        this.username = secretary.getUserInfo().getUsername();
        this.password = secretary.getUserInfo().getPassword();
        this.bornAt = secretary.getBornAt();
        this.age = (secretary.getBornAt().getYear() - LocalDate.now().getYear())*-1;
        this.contact = new ContactDTO(secretary.getContact());
        this.address = new AddressDTO(secretary.getAddress());
    }

    public static List<SecretaryDTO> convertSecretaryToDTO(List<Secretary> secretaries) {
        return secretaries.stream().map(SecretaryDTO::new).collect(Collectors.toList());
    }

}
