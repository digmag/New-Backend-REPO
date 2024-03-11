package com.example.Backend.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RequestDTO {

    private UUID id;

    private String name;
    private int number;
    private RequestStatus status;
    private LocalDateTime requestCreationDateTime;
    private LocalDateTime requestedDateTime;

    private UUID keyId;
    private UUID officeId;

    public RequestDTO(LocalDateTime requestedDateTime, UUID keyId, UUID officeId) {
        this.requestedDateTime = requestedDateTime;
        this.keyId = keyId;
        this.officeId = officeId;
    }

    public RequestDTO(RequestEntity entity, String name, int number){
        this.name = name;
        this.number = number;
        this.id = entity.getId();
        this.status = entity.getStatus();
        this.requestCreationDateTime = entity.getRequestCreationDateTime();
        this.requestedDateTime = entity.getRequestedDateTime();
        this.keyId = entity.getKey().getKeyId();
        this.officeId = entity.getOfficeId();
    }
    public RequestDTO(RequestCreateDTO requestCreateDTO){
        this.requestedDateTime = requestCreateDTO.getRequestedDateTime();
        this.requestCreationDateTime = LocalDateTime.now();
        this.keyId = requestCreateDTO.getKeyId();
        this.officeId = requestCreateDTO.getOfficeId();
    }
    public RequestDTO(RequestEntity entity){
        this.id = entity.getId();
        this.status = entity.getStatus();
        this.requestCreationDateTime = entity.getRequestCreationDateTime();
        this.requestedDateTime = entity.getRequestedDateTime();
        this.keyId = entity.getKey().getKeyId();
        this.officeId = entity.getOfficeId();
    }
}
