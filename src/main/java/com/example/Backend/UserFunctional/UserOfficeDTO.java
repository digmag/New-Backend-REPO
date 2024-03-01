package com.example.Backend.UserFunctional;

import com.example.Backend.Relations.UserToOffice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOfficeDTO {
    private String fullname;
    private String login;
    private String Role;
    public UserOfficeDTO(){

    }
    public UserOfficeDTO(UserToOffice userToOffice){
        this.Role = userToOffice.getRole();
        this.fullname = userToOffice.getUser().getFullname();
        this.login = userToOffice.getUser().getLogin();
    }
}
