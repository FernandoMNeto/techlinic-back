package br.com.clinic.services;

import br.com.clinic.api.in.PacientForm;
import br.com.clinic.entities.models.Address;
import br.com.clinic.entities.models.Contact;
import br.com.clinic.entities.models.Pacient;
import br.com.clinic.api.out.PacientDTO;
import br.com.clinic.error.resourcenotfound.ResourceNotFoundException;
import br.com.clinic.repositories.AddressRepository;
import br.com.clinic.repositories.ContactRepository;
import br.com.clinic.repositories.PacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PacientService {

    private final PacientRepository pacientRepository;
    private final AddressService addressService;
    private final ContactService contactService;


    @Autowired
    public PacientService(PacientRepository pacientRepository, AddressRepository addressRepository, AddressService addressService,
                          ContactRepository contactRepository, ContactService contactService) {
        this.pacientRepository = pacientRepository;
        this.addressService = addressService;
        this.contactService = contactService;
    }

    public ResponseEntity<List<PacientDTO>> allPacients() {

        List<Pacient> pacients = pacientRepository.findAll();

        return ResponseEntity.ok(PacientDTO.convertPacientToDTO(pacients));
    }

    public ResponseEntity<PacientDTO> pacientById(Long id) {
        Optional<Pacient> oPacient = pacientRepository.findById(id);

        if(oPacient.isEmpty()) {
            throw new ResourceNotFoundException("Pacient not found with ID: " + id);
        }
        return ResponseEntity.ok(new PacientDTO(oPacient.get()));
    }

    public ResponseEntity<PacientDTO> registerPacient(PacientForm pacientForm, UriComponentsBuilder uriBuilder) {

        Contact contact = pacientForm.getContact();
        Address address = pacientForm.getAddress();

        contactService.registerContact(contact);
        addressService.registerAddress(address);

        Pacient pacient = new Pacient(pacientForm);

        pacient.setContact(contact);
        pacient.setAddress(address);

        pacientRepository.save(pacient);

        URI uri = uriBuilder.path("/pacients/{id}").buildAndExpand(pacient.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacientDTO(pacient));
    }

    public ResponseEntity<PacientDTO> updatePacient(PacientForm pacientForm, Long id) {
        Optional<Pacient> oPacient = pacientRepository.findById(id);

        if (oPacient.isEmpty()) {
            throw new ResourceNotFoundException("Pacient not found with ID: " + id);
        }

        Pacient pacient = oPacient.get();

        contactService.updateContact(pacientForm.getContact());
        addressService.updateAddress(pacientForm.getAddress());

        pacient.setFirstName(pacientForm.getFirstName());
        pacient.setLastName(pacientForm.getLastName());
        pacient.setCpf(pacientForm.getCpf());
        pacient.setBornAt(pacientForm.getBornAt());

        pacientRepository.save(pacient);

        return ResponseEntity.ok(new PacientDTO(pacient));
    }


    public ResponseEntity<Void> deletePacient(Long id) {
        Optional<Pacient> oPacient = pacientRepository.findById(id);

        if (oPacient.isEmpty()) {
            throw new ResourceNotFoundException("Pacient not found with ID: " + id);
        }
        pacientRepository.delete(oPacient.get());

        return ResponseEntity.ok().build();
    }

}
