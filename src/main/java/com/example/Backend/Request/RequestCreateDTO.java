package com.example.Backend.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestCreateDTO {
    private Date requestedDateTime;
    private UUID keyId;
    private UUID officeId;
}
