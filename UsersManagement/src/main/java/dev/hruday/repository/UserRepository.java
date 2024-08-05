package dev.hruday.repository;

import dev.hruday.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findUserByEmail(String email);
    Boolean existsByEmail(String email);
}
