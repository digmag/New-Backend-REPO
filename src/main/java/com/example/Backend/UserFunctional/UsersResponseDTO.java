package com.example.Backend.UserFunctional;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsersResponseDTO {
    private List<UserResponseDTO> users = new ArrayList<UserResponseDTO>();

    public void addToList(UserResponseDTO userResponseDTO){ users.add(userResponseDTO); }
}
