package br.com.clinic.services;

import br.com.clinic.entities.models.Address;
import br.com.clinic.error.resourcenotfound.ResourceNotFoundException;
import br.com.clinic.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<Address> registerAddress(Address address) {
        return ResponseEntity.ok(addressRepository.save(address));
    }
    public ResponseEntity<Address> updateAddress(Address address) {

        Optional<Address> oAddress = addressRepository.findById(address.getId());

        if (oAddress.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with ID: " + address.getId());
        }

        Address updatedAddress = oAddress.get();
        updatedAddress.setCep(address.getCep());
        updatedAddress.setStreet(address.getStreet());
        updatedAddress.setDistrict(address.getDistrict());
        updatedAddress.setCity(address.getCity());
        updatedAddress.setState(address.getState());
        updatedAddress.setNumber(address.getNumber());
        updatedAddress.setComplement(address.getComplement());

        return ResponseEntity.ok(updatedAddress);
    }
}
