package br.com.clinic.entities.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String cep;
    @NotBlank
    private String street;
    @NotBlank
    private String district;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String number;
    private String complement;

    public Address(String cep, String street, String district, String city, String state, String number, String complement) {
        this.cep = cep;
        this.street = street;
        this.district = district;
        this.city = city;
        this.state = state;
        this.number = number;
        this.complement = complement;
    }
}
