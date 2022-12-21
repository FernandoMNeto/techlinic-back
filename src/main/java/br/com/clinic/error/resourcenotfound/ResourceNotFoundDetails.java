package br.com.clinic.error.resourcenotfound;

import br.com.clinic.error.model.ErrorDetail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResourceNotFoundDetails extends ErrorDetail {

    public ResourceNotFoundDetails() {
    }

    public static final class Builder {
        private String title;
        private int status;
        private String details;
        private LocalDateTime timestamp;
        private String developerMessage;

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

        public ResourceNotFoundDetails build() {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            resourceNotFoundDetails.setDeveloperMessage(developerMessage);
            resourceNotFoundDetails.setTitle(title);
            resourceNotFoundDetails.setDetails(details);
            resourceNotFoundDetails.setStatus(status);
            resourceNotFoundDetails.setTimestamp(timestamp);
            return resourceNotFoundDetails;
        }
    }
}
