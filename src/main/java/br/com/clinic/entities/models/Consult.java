package br.com.clinic.entities.models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.clinic.api.in.ConsultForm;
import br.com.clinic.enums.Situation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "consult")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private String description;
    private String complaint;
    private String diagnosis;
    private LocalTime time;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Situation situation;

    public Consult(ConsultForm consultForm, Pacient pacient, Doctor doctor) {
        this.pacient = pacient;
        this.doctor = doctor;
        this.description = consultForm.getDescription();
        this.complaint = consultForm.getComplaint();
        this.diagnosis = consultForm.getDiagnosis();
        this.time = consultForm.getTime();
        this.date = consultForm.getDate();
        this.situation = Situation.SCHEDULED;
    }
}
