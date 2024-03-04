package com.example.Backend.KeyFunctional;

import com.example.Backend.Request.RequestEntity;
import com.example.Backend.UserFunctional.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
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
    private UUID keyId;
    private UUID officeId;
    private String officeName;
    private Integer officeNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "key")
    private List<RequestEntity> requestList;

    public KeyEntity(KeyDTO keyDTO, UUID officeId){
        this.officeId = officeId;
        this.officeName = keyDTO.getOfficeName();
        this.officeNumber = keyDTO.getOfficeNumber();
    }

    public KeyEntity(){

    }
}
