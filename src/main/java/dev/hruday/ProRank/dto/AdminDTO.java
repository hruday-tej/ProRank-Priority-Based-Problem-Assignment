package dev.hruday.ProRank.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;
    private String user_name;
    private String user_password;
    private boolean is_admin = true;
}
