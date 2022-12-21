package br.com.clinic.api.out;

import br.com.clinic.entities.models.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {

    private Long id;
    private String cep;
    private String street;
    private String district;
    private String city;
    private String state;
    private String number;
    private String complement;

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.cep = address.getCep();
        this.street = address.getStreet();
        this.district = address.getDistrict();
        this.city = address.getCity();
        this.state = address.getState();
        this.number = address.getNumber();
        this.complement = address.getComplement();
    }
}
