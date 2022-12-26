package br.com.clinic.api.out;

import br.com.clinic.entities.models.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private String username;
    private String password;
    private Integer age;
    private LocalDate bornAt;
    private ContactDTO contact;
    private AddressDTO address;
    private List<ConsultDTO> consults;
    public DoctorDTO(Doctor doctor){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.username = doctor.getUserInfo().getUsername();
        this.password = doctor.getUserInfo().getPassword();
        this.cpf = doctor.getCpf();
        this.crm = doctor.getCrm();
        this.bornAt = doctor.getBornAt();
        this.age = (doctor.getBornAt().getYear() - LocalDate.now().getYear())*-1;
        this.consults = ConsultDTO.convertConsultToDTO(doctor.getConsults());
        this.contact = new ContactDTO(doctor.getContact());
        this.address = new AddressDTO(doctor.getAddress());
    }
    
    public static List<DoctorDTO> convertDoctorToDTO(List<Doctor> doctors) {
        return doctors.stream().map(DoctorDTO::new).collect(Collectors.toList());
    }


}
