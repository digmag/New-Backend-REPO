package com.example.Backend.UserFunctional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByLoginAndPassword(String login, String password);

    @Query("SELECT e FROM UserEntity e WHERE e.fullname LIKE %:name%")
    List<UserEntity> findAllByName(String name);
}
