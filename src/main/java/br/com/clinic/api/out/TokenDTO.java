package br.com.clinic.api.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {
    private String token;
    private String requestType;

    public TokenDTO(String token, String requestType) {
        this.token = token;
        this.requestType = requestType;
    }
}
