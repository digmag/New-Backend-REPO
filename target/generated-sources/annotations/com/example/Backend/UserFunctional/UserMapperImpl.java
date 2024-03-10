package com.example.Backend.UserFunctional;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        return userEntity;
    }

    @Override
    public UserDTO toDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        return userDTO;
    }
}
