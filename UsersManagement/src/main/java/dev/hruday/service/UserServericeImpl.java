package dev.hruday.service;


import dev.hruday.config.security.JWTGenerator;
import dev.hruday.controller.AuthenticationResponse;
import dev.hruday.dto.UserLoginDTO;
import dev.hruday.entity.Role;
import dev.hruday.entity.UserEntity;
import dev.hruday.entity.VerificationToken;
import dev.hruday.dto.UserDTO;
import dev.hruday.repository.UserRepository;
import dev.hruday.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;


@Service
public class UserServericeImpl implements UserService{
    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

//    private JWTGenerator jwtGenerator;

    private final JwtService jwtService;

    @Autowired
    public UserServericeImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, JwtService jwtService) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
//        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
//        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public AuthenticationResponse registerUser(UserDTO userDTO) {
        System.out.println("Inside here register user");
//        TODO:
//        return username is taken
        if(userRepository.existsByEmail(userDTO.getEmail())){
            System.out.println("User lready Exists");
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRole(Role.PARTICIPANT);
        System.out.println("Created new User Entity");

        userRepository.save(userEntity);

        System.out.println("User saved in db");

        var jwtToken = jwtService.generateToken(userEntity);
        System.out.println("token generated  ->  " + jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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
    public AuthenticationResponse loginUser(UserLoginDTO userLoginDTO) {
        System.out.println("User ttrying to login -> " + userLoginDTO.getEmail() + " --- " + userLoginDTO.getPassword());
        var user = userRepository.findUserByEmail(userLoginDTO.getEmail()).orElseThrow();
        System.out.println("User from db - > " + user.getEmail() + " --- " + user.getFirstName());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("User Logged In Successfully");

            var jwtToken = jwtService.generateToken(user);
        System.out.println("generating token from the db -> " + jwtToken);
            return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
