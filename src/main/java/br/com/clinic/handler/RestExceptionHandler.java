package br.com.clinic.handler;

import br.com.clinic.error.resourcenotfound.ResourceNotFoundDetails;
import br.com.clinic.error.resourcenotfound.ResourceNotFoundException;
import br.com.clinic.error.validation.ValidationErrorDetails;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
        ResourceNotFoundDetails rfnDetails = ResourceNotFoundDetails.Builder
                    .newBuilder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.NOT_FOUND.value())
                    .title("Resource not found")
                    .details(rfnException.getMessage())
                    .developerMessage(rfnException.getClass().getName())
                    .build();
            return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException) {

        HashMap<String, String> fieldsAndMessages = new HashMap<>();

        List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();

        for(FieldError fam: fieldErrors) {
            fieldsAndMessages.put(fam.getField(), fam.getDefaultMessage());
        }

        ValidationErrorDetails manvDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field Validation Error")
                .details("Field Validation Error in fields: " + fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", ")))
                .developerMessage(manvException.getClass().getName())
                .fieldsAndMessages(fieldsAndMessages)
                .build();
        return new ResponseEntity<>(manvDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException cvExcepcion) {
        ResourceNotFoundDetails cvDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Resource not found")
                .details(String.valueOf(cvExcepcion.getSQLException()))
                .developerMessage(cvExcepcion.getClass().getName())
                .build();

        return new ResponseEntity<>(cvDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException bcExcepcion) {
        ResourceNotFoundDetails bcDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Credentials")
                .details(String.valueOf(bcExcepcion.getMessage()))
                .developerMessage(bcExcepcion.getClass().getName())
                .build();

        return new ResponseEntity<>(bcDetails, HttpStatus.BAD_REQUEST);
    }

}
