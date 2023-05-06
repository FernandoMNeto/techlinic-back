package br.com.clinic.api.out;

import br.com.clinic.entities.models.Consult;
import br.com.clinic.entities.models.Doctor;
import br.com.clinic.entities.models.Pacient;
import br.com.clinic.enums.Situation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ConsultDTO {
    private Long id;
    private Long pacientId;
    private String pacientName;
    private String pacientCPF;
    private String doctorId;
    private String doctorName;
    private String description;
    private String complaint;
    private String diagnosis;
    private String situation;
    private String time;
    private String date;

    public ConsultDTO(Consult consult, Pacient pacient, Doctor doctor) {

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.id = consult.getId();
        this.pacientId = consult.getPacient().getId();
        this.pacientName = pacient.getFirstName() + " " + pacient.getLastName();
        this.pacientCPF = pacient.getCpf();
        this.doctorId = consult.getDoctor().getId().toString();
        this.doctorName = doctor.getFirstName() + " " + doctor.getLastName();
        this.description = consult.getDescription();
        this.complaint = consult.getComplaint();
        this.diagnosis = consult.getDiagnosis();
        this.situation = consult.getSituation().name();
        this.time = consult.getTime().format(formatterTime);
        this.date = consult.getDate().format(formatterDate);
    }

    public ConsultDTO(Consult consult) {

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.id = consult.getId();
        this.pacientId = consult.getPacient().getId();
        this.pacientName = consult.getPacient().getFirstName() + " " + consult.getPacient().getLastName();
        this.pacientCPF = consult.getPacient().getCpf();
        this.doctorId = consult.getDoctor().getId().toString();
        this.doctorName = consult.getDoctor().getFirstName() + " " + consult.getDoctor().getLastName();
        this.description = consult.getDescription();
        this.complaint = consult.getComplaint();
        this.diagnosis = consult.getDiagnosis();
        this.time = consult.getTime().format(formatterTime);
        this.date = consult.getDate().format(formatterDate);
    }

    public static List<ConsultDTO> convertConsultToDTO(List<Consult> dailyConsults) {
        return dailyConsults.stream().map(ConsultDTO::new).collect(Collectors.toList());
    }
}
