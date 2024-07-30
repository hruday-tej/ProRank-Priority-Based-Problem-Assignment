package dev.hruday.ProRank.Security.repository;

import dev.hruday.ProRank.Security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
