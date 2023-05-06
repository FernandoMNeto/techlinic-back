package br.com.clinic.entities.models;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import br.com.clinic.api.in.DoctorForm;
import br.com.clinic.entities.abstracts.People;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "doctor")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String crm;
    @Column(precision = 10, scale = 2)
    private BigDecimal consultValue;
    private LocalTime startWork;
    private LocalTime stopWork;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userinfo_id")
    private UserInfo userInfo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private List<Consult> consults;

    public Doctor(DoctorForm doctorForm) {
        super(doctorForm.getFirstName(), doctorForm.getLastName(),
                doctorForm.getCpf(), doctorForm.getBornAt(), doctorForm.getContact(), doctorForm.getAddress());
        this.userInfo = doctorForm.getUserInfo();
        this.crm = doctorForm.getCrm();
        this.consultValue = doctorForm.getConsultValue();
        this.startWork = doctorForm.getStartWork();
        this.stopWork = doctorForm.getStopWork();
        this.consults = new ArrayList<>();
    }
    public void addNewConsult(Consult consult) {
        this.consults.add(consult);
    }

}
