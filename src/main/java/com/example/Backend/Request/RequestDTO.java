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
    private String officeName;
    private Integer officeNumber;

    public RequestDTO(Date requestedDateTime, UUID keyId, String officeName, Integer officeNumber) {
        this.requestedDateTime = requestedDateTime;
        this.keyId = keyId;
        this.officeName = officeName;
        this.officeNumber = officeNumber;
    }
}
