package com.example.Backend.Relations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserToOfficeRepository extends JpaRepository<UserToOffice, UUID> {
    Optional<UserToOffice> findByUserId(UUID userid);
    List<Optional<UserToOffice>> findAllByOfficeId(UUID officeid);
}
