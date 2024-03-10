package com.example.Backend.OfficeFunctional;

import com.example.Backend.UserFunctional.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeEntity, UUID> {
    Optional<OfficeEntity> findByAdministratorId(UUID administratorID);

    @Query("SELECT e FROM OfficeEntity e WHERE e.name LIKE %:name%")
    List<OfficeEntity> findAllByName(String name);
}
