package dev.hruday.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + Constants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expiryDate)
                .setIssuedAt(currentDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return token;

    }

    public String getUsernameFromJWT(String token){
        Claims body = Jwts.parser().setSigningKey(Constants.SECRET_KEY_BASE64).build().parseSignedClaims(token).getPayload();


        return body.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(Constants.SECRET_KEY_BASE64).build().parseSignedClaims(token);
            return true;
        }catch (Exception exception){
            throw new AuthenticationCredentialsNotFoundException("Token Expired !");
        }
    }
}
