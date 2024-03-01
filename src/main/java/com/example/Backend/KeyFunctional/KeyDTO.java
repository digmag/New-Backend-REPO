package com.example.Backend.KeyFunctional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyDTO {
    private String officeName;
    private Integer officeNumber;

    public KeyDTO(KeyEntity keyEntity){
        this.officeName = keyEntity.getOfficeName();
        this.officeNumber = keyEntity.getOfficeNumber();
    }
    public KeyDTO(){}
}
