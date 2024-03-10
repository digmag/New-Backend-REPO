package com.example.Backend.Relations;

import com.example.Backend.UserFunctional.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserKeyRepository extends JpaRepository<UserKeyEntity, UUID> {
    List<UserKeyEntity> findAllUserKeyEntityByUserTo(UserEntity userTo);
}
