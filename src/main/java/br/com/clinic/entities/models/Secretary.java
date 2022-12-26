package br.com.clinic.entities.models;

import br.com.clinic.api.in.SecretaryForm;
import br.com.clinic.entities.abstracts.People;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "secretary")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Secretary extends People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userinfo_id")
    private UserInfo userInfo;

    public Secretary (SecretaryForm secretaryForm) {
        super(secretaryForm.getFirstName(), secretaryForm.getLastName(),
                secretaryForm.getCpf(), secretaryForm.getBornAt(), secretaryForm.getContact(), secretaryForm.getAddress());
        this.userInfo = secretaryForm.getUserInfo();
    }
}
