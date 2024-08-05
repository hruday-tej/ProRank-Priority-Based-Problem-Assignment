package dev.hruday.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserLoginDTO {
    private String email;
    @Column(length = 60)
    private String password;
}
