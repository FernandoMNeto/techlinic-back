package br.com.clinic.services;

import br.com.clinic.entities.models.Contact;
import br.com.clinic.error.resourcenotfound.ResourceNotFoundException;
import br.com.clinic.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ResponseEntity<Contact> registerContact(Contact contact) {
        return ResponseEntity.ok(contactRepository.save(contact));
    }

    public ResponseEntity<Contact> updateContact(Contact contact) {
        Optional<Contact> oContato = contactRepository.findById(contact.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (oContato.isEmpty()) {
            throw new ResourceNotFoundException("Contact not found with ID: " + contact.getId());
        }

        Contact updatedContato = oContato.get();

        updatedContato.setEmail(contact.getEmail());
        updatedContato.setPhone(contact.getPhone());
        updatedContato.setLastModified(LocalDate.now());
        contactRepository.save(updatedContato);

        return ResponseEntity.ok(updatedContato);
    }


}
