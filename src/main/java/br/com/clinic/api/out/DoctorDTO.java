package br.com.clinic.api.out;

import br.com.clinic.entities.models.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor
public class DoctorDTO {

    private Long id;
    private String cpf;
    private String crm;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate bornAt;
    private ContactDTO contact;
    private AddressDTO address;
    private List<ConsultDTO> consults;
    public DoctorDTO(Doctor doctor){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.cpf = doctor.getCpf();
        this.crm = doctor.getCrm();
        this.bornAt = doctor.getBornAt();
        this.age = Math.toIntExact(ChronoUnit.YEARS.between(doctor.getBornAt(), LocalDate.now()));
        this.consults = ConsultDTO.convertConsultToDTO(doctor.getConsults());
        this.contact = new ContactDTO(doctor.getContact());
        this.address = new AddressDTO(doctor.getAddress());
    }
    
    public static List<DoctorDTO> convertDoctorToDTO(List<Doctor> doctors) {
        return doctors.stream().map(DoctorDTO::new).collect(Collectors.toList());
    }


}
