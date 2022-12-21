package br.com.clinic.error.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetail {

    private String title;
    private int status;
    private String details;
    private LocalDateTime timestamp;
    private String developerMessage;

}
