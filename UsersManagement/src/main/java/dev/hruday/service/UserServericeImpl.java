package dev.hruday.service;


import dev.hruday.config.security.JWTGenerator;
import dev.hruday.dto.AuthReponseDTO;
import dev.hruday.dto.UserLoginDTO;
import dev.hruday.entity.Role;
import dev.hruday.entity.UserEntity;
import dev.hruday.entity.VerificationToken;
import dev.hruday.dto.UserDTO;
import dev.hruday.repository.RoleRepository;
import dev.hruday.repository.UserRepository;
import dev.hruday.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;


@Service
public class UserServericeImpl implements UserService{
    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private JWTGenerator jwtGenerator;

    @Autowired
    public UserServericeImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public UserEntity registerUser(UserDTO userDTO) {
//        TODO:
//        return username is taken
        if(userRepository.existsByEmail(userDTO.getEmail())){
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role;

        if(userEntity.getEmail().contains(".org")){
            System.out.println(roleRepository.findRoleByRoleName("HOST"));
            role = roleRepository.findRoleByRoleName("HOST").get();
        }else{
            System.out.println(roleRepository.findRoleByRoleName("PARTICIPANT"));
            role = roleRepository.findRoleByRoleName("PARTICIPANT").get();
        }

        userEntity.setRoles(Collections.singletonList(role));

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
    public ResponseEntity<AuthReponseDTO> loginUser(UserLoginDTO userLoginDTO) {
//        try {
            // Attempt to authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(new AuthReponseDTO(token), HttpStatus.OK);
//        } catch (BadCredentialsException e) {
//            // Handle incorrect username or password
//            return new ResponseEntity<Optional>("Invalid username or password", HttpStatus.UNAUTHORIZED);
//        } catch (InternalAuthenticationServiceException e) {
//            // Handle other internal authentication issues
//            return new ResponseEntity<Optional>("Authentication service error", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (AuthenticationException e) {
//            // Handle general authentication exceptions
//            return new ResponseEntity<String>("Authentication failed", HttpStatus.UNAUTHORIZED);
//        }
    }
}
