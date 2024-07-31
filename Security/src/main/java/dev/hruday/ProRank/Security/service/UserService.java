package dev.hruday.ProRank.Security.service;

import dev.hruday.ProRank.Security.entity.User;
import dev.hruday.ProRank.Security.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);
}
