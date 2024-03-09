package com.example.Backend.UserFunctional;

import com.example.Backend.Errors.AppException;
import com.example.Backend.TokenFunctional.*;
import com.example.Backend.statusCode.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private final TokenRepository tokenRepository;
    @Override
    @SneakyThrows
    public TokenDTO registration(UserDTO userDTO) {
        if(userRepository
                .findByLogin(userDTO.getLogin())
                .isPresent())
        {
            throw new AppException(400, "Login already taken");
        }
        UserEntity userEntity = new UserEntity(userDTO);
        TokenDTO tokenDTO = new TokenDTO();
        userRepository.save(userEntity);
        tokenDTO.setToken(TokenValueGenerator.generator(
                userRepository.findByLogin(userDTO.getLogin()).get().getId(),
                userDTO.getLogin()));
        UUID userid = userRepository
                .findByLogin(userDTO.getLogin()).get().getId();
        TokenEntity tokenEntity = new TokenEntity(tokenDTO);
        tokenEntity.setUserid(userid);
        tokenRepository.save(tokenEntity);
        return tokenDTO;
    }

    @SneakyThrows
    @Override
    public TokenDTO login(UserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());
        if (userEntityOptional.isEmpty()){
            throw new AppException(400, "Wrong data");
        }
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(TokenValueGenerator.generator(
                userEntityOptional.get().getId(), userDTO.getLogin()
        ));
        UUID userid = userEntityOptional.get().getId();
        TokenEntity tokenEntity = new TokenEntity(tokenDTO);
        tokenEntity.setUserid(userid);
        tokenRepository.save(tokenEntity);
        return tokenDTO;
    }

    @Override
    @SneakyThrows
    public UserViewDTO viewInfo(String tokenValue) {
        Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByValue(tokenValue);
        if(tokenEntityOptional.isEmpty()){
            throw new AppException(401, "Unauthorized");
        }
        Optional<UserEntity> userEntityOptional = userRepository.findById(
                tokenEntityOptional.get().getUserid()
        );
        UserEntity userEntity = (userEntityOptional.isPresent()) ? userEntityOptional.get() : null;
        return new UserViewDTO(userEntity);
    }

    @SneakyThrows
    @Override
    public StatusCode logout(String tokenValue) {
        Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByValue(tokenValue);
        if(tokenEntityOptional.isEmpty()){
            throw new AppException(401, "Unauthorized");
        }
        tokenRepository.deleteById(tokenEntityOptional.get().getId());
        return new StatusCode(200, "Выход");
    }

    @SneakyThrows
    @Override
    public UsersResponseDTO Allusers(String token, String name) {
        if(tokenRepository.findByValue(token).isEmpty()){
            throw new AppException(401, "Unauthorized");
        }
        UsersResponseDTO usersResponseDTO = new UsersResponseDTO();
        userRepository.findAllByFullname(name).forEach(userEntity -> {
            UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);
            usersResponseDTO.addToList(userResponseDTO);
        });
        return usersResponseDTO;
    }

}
