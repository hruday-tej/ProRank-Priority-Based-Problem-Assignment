package dev.hruday.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthReponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthReponseDTO(String accessToken){
        this.accessToken   = accessToken;
    }

}
