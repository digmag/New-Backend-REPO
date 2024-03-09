package com.example.Backend.UserFunctional;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String fullName;

    public UserResponseDTO(UserEntity userEntity){
        this.id = userEntity.getId();
        this.fullName = userEntity.getFullname();
    }
}
