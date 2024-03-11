package com.example.Backend.Request;

import com.example.Backend.KeyFunctional.KeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<RequestEntity, UUID> {
    Optional<List<RequestEntity>> findRequestEntitiesByUserId(UUID userId);

    Optional<List<RequestEntity>> findAllByOfficeId(UUID officeId);

    Optional<List<RequestEntity>> findAllByKeyAndRequestedDateTimeAndStatus(KeyEntity keyEntity, Date requestedDateTime, RequestStatus status);
}
