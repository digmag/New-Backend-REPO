package com.example.Backend.KeyFunctional;

import com.example.Backend.UserFunctional.UserViewDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class KeyDTO {
    private UUID keyId;
    private UUID officeID;
    private String officeName;
    private Integer officeNumber;
    private UserViewDTO userViewDTO;

    public KeyDTO(KeyEntity keyEntity){
        this.keyId = keyEntity.getKeyId();
        this.officeName = keyEntity.getOfficeName();
        this.officeNumber = keyEntity.getOfficeNumber();
        this.officeID = keyEntity.getOfficeId();
        if(keyEntity.getUser() != null){
            this.userViewDTO = new UserViewDTO(keyEntity.getUser());
        }
    }
    public KeyDTO(){}
}
