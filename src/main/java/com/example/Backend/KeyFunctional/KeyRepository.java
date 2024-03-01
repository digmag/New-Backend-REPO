package com.example.Backend.KeyFunctional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface KeyRepository extends JpaRepository<KeyEntity, UUID> {
    Optional<List<KeyEntity>> findByOfficeId(UUID officeid);
}
