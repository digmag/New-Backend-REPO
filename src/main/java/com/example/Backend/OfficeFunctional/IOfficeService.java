package com.example.Backend.OfficeFunctional;

import com.example.Backend.UserFunctional.UserAppointmentDTO;
import com.example.Backend.UserFunctional.UserDTO;
import com.example.Backend.UserFunctional.UserOfficeDTO;
import com.example.Backend.UserFunctional.UserViewDTO;
import com.example.Backend.statusCode.StatusCode;

import java.util.List;
import java.util.UUID;

public interface IOfficeService {
    OfficeDTO createOffice(OfficeCreateDTO officeCreateDTO,String tokenValue);
    StatusCode appointment(UUID Officeid, UserAppointmentDTO userAppointmentDTO, String tokenValue);
    OfficeUsers viewAll(String tokenValue, UUID officeid);

    OfficeAllInfoDTO allinfo(String tokenValue, UUID officeid);
}
