package dev.hruday.service;


import dev.hruday.dto.UserLoginDTO;
import dev.hruday.entity.UserEntity;
import dev.hruday.entity.VerificationToken;
import dev.hruday.dto.UserDTO;
import dev.hruday.repository.UserRepository;
import dev.hruday.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;


@Service
public class UserServericeImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity registerUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
//        userEntity.setRole("USER");
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public void saveVerificationTokenForUser(String token, UserEntity userEntity) {
        VerificationToken verificationToken = new VerificationToken(userEntity, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null)return "Invalid Token";

        UserEntity userEntity = verificationToken.getUserEntity();
        Calendar calendar = Calendar.getInstance();

        if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "Expired";
        }

        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        return "valid";
    }

    @Override
    public UserEntity loginUser(UserLoginDTO userLoginDTO) {
        return userRepository.findUserByEmail(userLoginDTO.getEmail());
    }
}
