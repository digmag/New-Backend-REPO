package com.example.Backend.Request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<RequestEntity, UUID> {
    Optional<List<RequestEntity>> findRequestEntitiesByUserId(UUID userId);
}
