package br.com.clinic.entities.models;

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
public class Doctor extends People implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String crm;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private List<Consult> consults;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public Doctor(DoctorForm doctorForm) {
        super(doctorForm.getFirstName(), doctorForm.getLastName(),
                doctorForm.getCpf(), doctorForm.getBornAt(), doctorForm.getContact(), doctorForm.getAddress());
        this.username = doctorForm.getUsername();
        this.password = doctorForm.getPassword();
        this.crm = doctorForm.getCrm();
        this.consults = new ArrayList<>();
    }
    public void addNewConsult(Consult consult) {
        this.consults.add(consult);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
