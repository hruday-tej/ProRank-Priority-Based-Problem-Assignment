package dev.hruday.service;


import dev.hruday.entity.User;
import dev.hruday.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);
}
