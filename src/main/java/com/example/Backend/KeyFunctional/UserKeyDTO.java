package com.example.Backend.KeyFunctional;

import com.example.Backend.Relations.UserKeyEntity;
import com.example.Backend.UserFunctional.UserResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserKeyDTO {
    private UUID id;
    private UserResponseDTO userFrom;
    private UserResponseDTO userTo;
    private KeyDTO keyDTO;
    public UserKeyDTO(UserKeyEntity userKeyEntity){
        this.id = userKeyEntity.getId();
        this.userFrom = new UserResponseDTO(userKeyEntity.getUserFrom());
        this.userTo = new UserResponseDTO(userKeyEntity.getUserTo());
        this.keyDTO = new KeyDTO(userKeyEntity.getKeyEntity());
    }
}
