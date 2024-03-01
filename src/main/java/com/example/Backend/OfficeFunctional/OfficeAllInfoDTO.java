package com.example.Backend.OfficeFunctional;

import com.example.Backend.KeyFunctional.KeyDTO;
import com.example.Backend.UserFunctional.UserOfficeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OfficeAllInfoDTO {
    private UUID id;
    private String name;
    private UUID administratorId;
    private List<KeyDTO> officesKeys;
    private List<UserOfficeDTO> Users;

    public OfficeAllInfoDTO(OfficeAllinfoModel officeAllinfoModel){
        this.id = officeAllinfoModel.getId();
        this.name = officeAllinfoModel.getName();
        this.administratorId = officeAllinfoModel.getAdministratorId();
        this.officesKeys = officeAllinfoModel.getOfficesKeys();
        this.Users = officeAllinfoModel.getUserOfficeDTOS();
    }
}
