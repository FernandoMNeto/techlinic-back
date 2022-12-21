package br.com.clinic.api.out;

import br.com.clinic.entities.models.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {

    private Long id;
    private String email;
    private String phone;
    private String lastModified;

    public ContactDTO(Contact contact) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.id = contact.getId();
        this.email = contact.getEmail();
        this.phone = contact.getPhone();
        this.lastModified = contact.getLastModified().format(formatter);
    }
}
