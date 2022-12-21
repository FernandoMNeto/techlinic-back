package br.com.clinic.entities.models;

import br.com.clinic.api.in.SecretaryForm;
import br.com.clinic.entities.abstracts.People;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "secretary")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Secretary extends People implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public Secretary (SecretaryForm secretaryForm) {
        super(secretaryForm.getFirstName(), secretaryForm.getLastName(),
                secretaryForm.getCpf(), secretaryForm.getBornAt(), secretaryForm.getContact(), secretaryForm.getAddress());
        this.username = secretaryForm.getUsername();
        this.password = secretaryForm.getPassword();
    }

    public void addRole(Role role) { this.roles.add(role); }

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
