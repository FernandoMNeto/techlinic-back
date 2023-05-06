package br.com.clinic.api.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ConsultForm {

    @NotNull
    private Long pacientId;
    @NotNull
    private Long doctorId;
    @NotBlank
    private String description;
    @NotBlank
    private String complaint;
    @NotBlank
    private String diagnosis;
    @NotBlank
    private String prescription;
    @NotNull
    private LocalTime time;
    @NotNull
    private LocalDate date;

}
