package com.example.Backend.UserFunctional;

import com.example.Backend.TokenFunctional.TokenDTO;
import com.example.Backend.statusCode.StatusCode;

public interface IUserService {
    TokenDTO registration(UserDTO userDTO);
    TokenDTO login(UserDTO userDTO);
    UserViewDTO viewInfo(String tokenValue);
    StatusCode logout(String tokenValue);

    UsersResponseDTO Allusers(String token,String name);
}
