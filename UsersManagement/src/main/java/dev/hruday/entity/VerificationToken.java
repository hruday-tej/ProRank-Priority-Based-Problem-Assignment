package dev.hruday.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private UserEntity userEntity;

    public VerificationToken(UserEntity userEntity, String token){
        super();
        this.token = token;
        this.userEntity = userEntity;
        this.expirationTime = calcilateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calcilateExpirationDate(EXPIRATION_TIME);
    }

    private Date calcilateExpirationDate(int expirationTime) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(new Date().getTime());
        calender.add(calender.MINUTE, expirationTime);
        return new Date(calender.getTime().getTime());
    }
}
