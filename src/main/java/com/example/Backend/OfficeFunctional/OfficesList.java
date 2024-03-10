package com.example.Backend.OfficeFunctional;

import com.example.Backend.UserFunctional.UserResponseDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OfficesList {
    private List<OfficeDTO> offices = new ArrayList<OfficeDTO>();

    public void addToList(OfficeDTO officeDTO){ offices.add(officeDTO); }
}
