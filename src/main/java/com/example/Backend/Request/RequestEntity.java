package com.example.Backend.Request;

import com.example.Backend.KeyFunctional.KeyEntity;
import com.example.Backend.UserFunctional.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Request")
@Getter
@Setter
@NoArgsConstructor
public class RequestEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private UUID userId;

    private RequestStatus status;
    private LocalDateTime requestCreationDateTime;
    private LocalDateTime requestedDateTime;

    @ManyToOne
    private KeyEntity key;

    public RequestEntity(UUID userId, RequestDTO requestDTO, KeyEntity key) {
        this.userId = userId;
        this.status = RequestStatus.IN_PROCESS;
        this.requestCreationDateTime = requestDTO.getRequestCreationDateTime();
        this.requestedDateTime = requestDTO.getRequestedDateTime();
        this.key = key;
    }
}
