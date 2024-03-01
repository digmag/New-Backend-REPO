package com.example.Backend.OfficeFunctional;

import com.example.Backend.KeyFunctional.KeyDTO;
import com.example.Backend.UserFunctional.UserOfficeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OfficeAllinfoModel {
    private UUID id;
    private String name;
    private UUID administratorId;
    private List<KeyDTO> officesKeys = new ArrayList<>();
    private List<UserOfficeDTO> userOfficeDTOS = new ArrayList<>();

    public OfficeAllinfoModel(OfficeEntity officeEntity){
        this.id = officeEntity.getId();
        this.name = officeEntity.getName();
        this.administratorId = officeEntity.getAdministratorId();
    }
    public void addKey(KeyDTO keyDTO){
        this.officesKeys.add(keyDTO);
    }

    public void addUser(UserOfficeDTO userOfficeDTO){
        this.userOfficeDTOS.add(userOfficeDTO);
    }

}
