package com.example.Backend.KeyFunctional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class KeyEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID keyId;
    UUID officeId;
    String officeName;
    Integer officeNumber;

    public KeyEntity(KeyDTO keyDTO, UUID officeId){
        this.officeId = officeId;
        this.officeName = keyDTO.getOfficeName();
        this.officeNumber = keyDTO.getOfficeNumber();
    }

    public KeyEntity(){

    }
}
