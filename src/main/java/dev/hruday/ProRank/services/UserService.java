package dev.hruday.ProRank.services;

import dev.hruday.ProRank.dto.UserDTO;
import dev.hruday.ProRank.entities.UserEntity;
import dev.hruday.ProRank.repositories.UserRepository;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;
    @Autowired
    private UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public UserDTO getUserById(Long userId){
        UserEntity userEntity = this.userRepository.getById(userId);
        return this.modelMapper.map(userEntity, UserDTO.class);
    }
    public UserDTO createNewUser(UserDTO userDTO){
        System.out.println("WE ARE INSIDE THE HERE");
        UserEntity userEntityMapped = modelMapper.map(userDTO, UserEntity.class);
        return modelMapper.map(this.userRepository.save(userEntityMapped), UserDTO.class);
    }
}
