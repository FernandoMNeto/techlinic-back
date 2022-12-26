package br.com.clinic.services;

import br.com.clinic.api.in.SecretaryForm;
import br.com.clinic.api.out.SecretaryDTO;
import br.com.clinic.entities.models.Address;
import br.com.clinic.entities.models.Contact;
import br.com.clinic.entities.models.Secretary;
import br.com.clinic.entities.models.UserInfo;
import br.com.clinic.error.resourcenotfound.ResourceNotFoundException;
import br.com.clinic.repositories.SecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class SecretaryService {

    private final SecretaryRepository secretaryRepository;
    private final ContactService contactService;
    private final AddressService addressService;
    private final UserInfoService userInfoService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecretaryService(SecretaryRepository secretaryRepository, ContactService contactService,
                            AddressService addressService, UserInfoService userInfoService, RoleService roleService,
                            PasswordEncoder passwordEncoder) {
        this.secretaryRepository = secretaryRepository;
        this.contactService = contactService;
        this.addressService = addressService;
        this.userInfoService = userInfoService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<List<SecretaryDTO>> allSecretaries() {

        List<Secretary> secretaries = secretaryRepository.findAll();

        return ResponseEntity.ok(SecretaryDTO.convertSecretaryToDTO(secretaries));
    }

    public ResponseEntity<SecretaryDTO> secretaryById(Long id) {

        Optional<Secretary> oSecretary = secretaryRepository.findById(id);

        if (oSecretary.isEmpty()) {
            throw new ResourceNotFoundException("Secretary not found with ID: " + id);
        }

        return ResponseEntity.ok(new SecretaryDTO(oSecretary.get()));
    }

    public ResponseEntity<SecretaryDTO> registerSecretary(SecretaryForm secretaryForm,
                                                          UriComponentsBuilder uriBuilder) {
        Contact contact = secretaryForm.getContact();
        Address address = secretaryForm.getAddress();
        UserInfo userInfo = secretaryForm.getUserInfo();

        contactService.registerContact(contact);
        userInfoService.registerUserInfo(userInfo);
        addressService.registerAddress(address);

        Secretary secretary = new Secretary(secretaryForm);
        secretary.getUserInfo().setPassword(passwordEncoder.encode(secretary.getUserInfo().getPassword()));
        secretary.getUserInfo().addRole(roleService.registerRole("SECRETARY"));
        secretary.setContact(contact);
        secretary.setAddress(address);

        secretaryRepository.save(secretary);

        URI uri = uriBuilder.path("/secretaries/{id}").buildAndExpand(secretary.getId()).toUri();
        return ResponseEntity.created(uri).body(new SecretaryDTO(secretary));
    }

    public ResponseEntity<SecretaryDTO> updateSecretary(SecretaryForm secretaryForm, Long id) {
        Optional<Secretary> oSecretary = secretaryRepository.findById(id);

        if (oSecretary.isEmpty()) {
            throw new ResourceNotFoundException("Secretary not found with ID: " + id);
        }

        Secretary secretary = oSecretary.get();

        contactService.updateContact(secretaryForm.getContact());
        addressService.updateAddress(secretaryForm.getAddress());

        secretary.setFirstName(secretaryForm.getFirstName());
        secretary.setLastName(secretaryForm.getLastName());
        secretary.setCpf(secretaryForm.getCpf());
        secretary.setBornAt(secretaryForm.getBornAt());

        secretaryRepository.save(secretary);

        return ResponseEntity.ok(new SecretaryDTO(secretary));
    }

    public ResponseEntity<Void> deleteSecretary(Long id) {
        Optional<Secretary> oSecretary = secretaryRepository.findById(id);
        if (oSecretary.isEmpty()) {
            throw new ResourceNotFoundException("Secretary not found with ID: " + id);
        }
        secretaryRepository.delete(oSecretary.get());
        return ResponseEntity.ok().build();
    }
}
