package br.com.clinic.services;

import br.com.clinic.entities.models.Role;
import br.com.clinic.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role registerRole(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);

        if (role.isEmpty()) {
            return roleRepository.save(new Role(roleName));
        }

       return role.get();
    }
}
