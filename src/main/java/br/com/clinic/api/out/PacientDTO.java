package br.com.clinic.api.out;

import br.com.clinic.entities.models.Pacient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PacientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate bornAt;
    private Integer age;
    private String cpf;

    private ContactDTO contact;
    private AddressDTO address;
    private List<ConsultDTO> consults;
    public PacientDTO(Pacient pacient){
        this.id = pacient.getId();
        this.firstName = pacient.getFirstName();
        this.lastName = pacient.getLastName();
        this.cpf = pacient.getCpf();
        this.bornAt = pacient.getBornAt();
        this.age = Math.toIntExact(ChronoUnit.YEARS.between(pacient.getBornAt(), LocalDate.now()));
        this.consults = ConsultDTO.convertConsultToDTO(pacient.getConsults()) ;
        this.contact = new ContactDTO(pacient.getContact());
        this.address = new AddressDTO(pacient.getAddress());

    }
    public static List<PacientDTO> convertPacientToDTO(List<Pacient> pacients) {
        return pacients.stream().map(PacientDTO::new).collect(Collectors.toList());
    }

}
