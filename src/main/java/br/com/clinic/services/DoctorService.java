package br.com.clinic.services;

import br.com.clinic.api.in.DoctorForm;
import br.com.clinic.entities.models.*;
import br.com.clinic.api.out.DoctorDTO;
import br.com.clinic.error.resourcenotfound.ResourceNotFoundException;
import br.com.clinic.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements UserDetailsService {

    private final DoctorRepository doctorRepository;
    private final ContactService contactService;
    private final AddressService addressService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ContactService contactService,
                         AddressService addressService, PasswordEncoder passwordEncoder,
                         RoleService roleService) {
        this.doctorRepository = doctorRepository;
        this.contactService = contactService;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public ResponseEntity<List<DoctorDTO>> allDoctors() {

        List<Doctor> doctors = doctorRepository.findAll();

        return ResponseEntity.ok(DoctorDTO.convertDoctorToDTO(doctors));
    }

    public ResponseEntity<DoctorDTO> doctorById(Long id) {

        Optional<Doctor> oDoctor = doctorRepository.findById(id);

        if (oDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }

        return ResponseEntity.ok(new DoctorDTO(oDoctor.get()));
    }

    public ResponseEntity<DoctorDTO> registerDoctor(DoctorForm doctorForm, UriComponentsBuilder uriBuilder) {

        Contact contact = doctorForm.getContact();
        Address address = doctorForm.getAddress();

        contactService.registerContact(contact);
        addressService.registerAddress(address);

        Doctor doctor = new Doctor(doctorForm);
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.addRole(roleService.registerRole("DOCTOR"));
        doctor.setContact(contact);
        doctor.setAddress(address);

        doctorRepository.save(doctor);

        URI uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
        return ResponseEntity.created(uri).body(new DoctorDTO(doctor));
    }

    public ResponseEntity<DoctorDTO> updateDoctor(DoctorForm doctorForm, Long id) {

        Optional<Doctor> oDoctor = doctorRepository.findById(id);

        if (oDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }

        Doctor doctor = oDoctor.get();

        contactService.updateContact(doctorForm.getContact());
        addressService.updateAddress(doctorForm.getAddress());

        doctor.setFirstName(doctorForm.getFirstName());
        doctor.setLastName(doctorForm.getLastName());
        doctor.setCrm(doctorForm.getCrm());
        doctor.setCpf(doctorForm.getCpf());
        doctor.setUsername(doctorForm.getUsername());
        doctor.setPassword(passwordEncoder.encode(doctorForm.getPassword()));
        doctor.setBornAt(doctorForm.getBornAt());


        return ResponseEntity.ok(new DoctorDTO(doctor));
    }

    public ResponseEntity<Void> deleteDoctor(Long id) {
        Optional<Doctor> oDoctor = doctorRepository.findById(id);
        if (oDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }
        doctorRepository.delete(oDoctor.get());
        return ResponseEntity.ok().build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Username not found"));
        return new User(doctor.getUsername(), doctor.getPassword(), doctor.getAuthorities());
    }
}
