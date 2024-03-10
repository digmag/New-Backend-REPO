package com.example.Backend.TokenFunctional;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)"
)
@Component
public class TokenMapperImpl implements TokenMapper {

    @Override
    public TokenDTO toDTO(TokenEntity tokenEntity) {
        if ( tokenEntity == null ) {
            return null;
        }

        TokenDTO tokenDTO = new TokenDTO();

        return tokenDTO;
    }

    @Override
    public TokenEntity toEntity(TokenDTO tokenDTO) {
        if ( tokenDTO == null ) {
            return null;
        }

        TokenEntity tokenEntity = new TokenEntity();

        return tokenEntity;
    }
}
