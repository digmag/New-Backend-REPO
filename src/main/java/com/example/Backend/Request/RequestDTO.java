package com.example.Backend.Request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class RequestDTO {

    private UUID id;

    private RequestStatus status;
    private Date requestCreationDateTime;
    private Date requestedDateTime;

    private UUID keyId;
    private UUID officeId;

    public RequestDTO(Date requestedDateTime, UUID keyId, UUID officeId) {
        this.requestedDateTime = requestedDateTime;
        this.keyId = keyId;
        this.officeId = officeId;
    }

    public RequestDTO(RequestEntity entity){
        this.id = entity.getId();
        this.status = entity.getStatus();
        this.requestCreationDateTime = entity.getRequestCreationDateTime();
        this.requestedDateTime = entity.getRequestedDateTime();
        this.keyId = entity.getKey().getKeyId();
        this.officeId = entity.getOfficeId();
    }
    public RequestDTO(RequestCreateDTO requestCreateDTO){
        this.requestedDateTime = requestCreateDTO.getRequestedDateTime();
        this.requestCreationDateTime = new Date();
        this.keyId = requestCreateDTO.getKeyId();
        this.officeId = requestCreateDTO.getOfficeId();
    }

}
