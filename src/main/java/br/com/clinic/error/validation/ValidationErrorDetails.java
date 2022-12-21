package br.com.clinic.error.validation;

import br.com.clinic.error.model.ErrorDetail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
public class ValidationErrorDetails extends ErrorDetail {

    private HashMap<String, String> fieldsAndMessages;

    public static final class Builder {
        private String title;
        private int status;
        private String details;
        private LocalDateTime timestamp;
        private String developerMessage;
        private HashMap<String, String> fieldsAndMessages;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder fieldsAndMessages(HashMap<String, String> fieldAndMessages) {
            this.fieldsAndMessages = fieldAndMessages;
            return this;
        }

        public ValidationErrorDetails build() {
            ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
            validationErrorDetails.setDeveloperMessage(developerMessage);
            validationErrorDetails.setTitle(title);
            validationErrorDetails.setDetails(details);
            validationErrorDetails.setStatus(status);
            validationErrorDetails.setTimestamp(timestamp);
            validationErrorDetails.fieldsAndMessages = fieldsAndMessages;
            return validationErrorDetails;
        }
    }
}
