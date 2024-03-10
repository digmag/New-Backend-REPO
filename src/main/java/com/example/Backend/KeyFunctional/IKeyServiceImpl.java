package com.example.Backend.KeyFunctional;

import com.example.Backend.Errors.AppException;
import com.example.Backend.Relations.UserKeyEntity;
import com.example.Backend.Relations.UserKeyRepository;
import com.example.Backend.Relations.UserToOffice;
import com.example.Backend.Relations.UserToOfficeRepository;
import com.example.Backend.TokenFunctional.TokenRepository;
import com.example.Backend.UserFunctional.UserEntity;
import com.example.Backend.UserFunctional.UserRepository;
import com.example.Backend.statusCode.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IKeyServiceImpl implements IKeyService {
    private final KeyRepository keyRepository;
    private final UserToOfficeRepository userToOfficeRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserKeyRepository userKeyRepository;
    @SneakyThrows
    @Override
    public KeyDTO createKey(UUID officeId, String tokenvalue, KeyDTO keyDTO) {
        if(tokenRepository.findByValue(tokenvalue).isEmpty()){
            throw new AppException(401,"Unauthorized");
        }
        if(userToOfficeRepository.findByUserIdAndOfficeId(tokenRepository.findByValue(tokenvalue).get().getUserid(), officeId).isEmpty()){
            throw new AppException(400, "Пользователь не принадлежит к данному офису");
        }
        UserToOffice admin = userToOfficeRepository.findByUserIdAndOfficeId(tokenRepository.findByValue(tokenvalue).get().getUserid(), officeId).get();
        if(!admin.getRole().equals("Administrator") || admin.getRole().equals("Worker")){
            throw new AppException(403, "Невозможно создать ключ");
        }
        KeyEntity keyEntity = new KeyEntity(keyDTO,officeId);
        keyRepository.save(keyEntity);
        return keyDTO;
    }

    @SneakyThrows
    @Override
    public KeyDTO editKey(UUID officeId, String tokenvalue, KeyDTO keyDTO) {
        if(tokenRepository.findByValue(tokenvalue).isEmpty()){
            throw new AppException(401,"Unauthorized");
        }
        if(userToOfficeRepository.findByUserIdAndOfficeId(tokenRepository.findByValue(tokenvalue).get().getUserid(), officeId).isEmpty()){
            throw new AppException(400, "Пользователь не принадлежит к данному офису");
        }
        UserToOffice admin = userToOfficeRepository.findByUserIdAndOfficeId(tokenRepository.findByValue(tokenvalue).get().getUserid(), officeId).get();
        if(!admin.getRole().equals("Administrator") || admin.getRole().equals("Worker")){
            throw new AppException(403, "Невозможно изменить ключ");
        }
        if(keyRepository.findById(keyDTO.getKeyId()).isEmpty()){
            throw new AppException(404, "Id ключа не найден");
        }
        KeyEntity keyEntity = keyRepository.findById(keyDTO.getKeyId()).get();
        keyEntity.setOfficeName(keyDTO.getOfficeName());
        keyEntity.setOfficeNumber(keyDTO.getOfficeNumber());
        keyRepository.save(keyEntity);
        return keyDTO;
    }

    @SneakyThrows
    @Override
    public StatusCode deleteKey(UUID officeId, String tokenvalue, UUID keyId) {
        if(tokenRepository.findByValue(tokenvalue).isEmpty()){
            throw new AppException(401,"Unauthorized");
        }
        if(userToOfficeRepository.findByUserIdAndOfficeId(tokenRepository.findByValue(tokenvalue).get().getUserid(), officeId).isEmpty()){
            throw new AppException(400, "Пользователь не принадлежит к данному офису");
        }
        UserToOffice admin = userToOfficeRepository.findByUserIdAndOfficeId(tokenRepository.findByValue(tokenvalue).get().getUserid(), officeId).get();
        if(!admin.getRole().equals("Administrator") || admin.getRole().equals("Worker")){
            throw new AppException(403, "Невозможно создать ключ");
        }
        if(keyRepository.findById(keyId).isEmpty()){
            throw new AppException(404, "Id ключа не найден");
        }
        keyRepository.deleteById(keyId);
        return new StatusCode(200, "Ключ удален");
    }

    @SneakyThrows
    @Override
    public List<KeyDTO> myKeys(String tokenValue) {
        if(tokenRepository.findByValue(tokenValue).isEmpty()){
            throw new AppException(401, "Unautorized");
        }
        Optional<UserEntity> userEntity = userRepository.findById(tokenRepository.findByValue(tokenValue).get().getUserid());
        if(userEntity.isEmpty()){
            throw new AppException(401, "Unautorized");
        }
        List<KeyEntity> keyEntities = keyRepository.findAllByUser(userEntity.get());
        List<KeyDTO> keyDTOS = new ArrayList<>();
        keyEntities.forEach(keyEntity -> {
            keyDTOS.add(new KeyDTO(keyEntity));
        });
        return keyDTOS;
    }

    @SneakyThrows
    @Override
    public KeysList listOfKeys(String token) {
        if(tokenRepository.findByValue(token).isEmpty()){
            throw new AppException(401, "Unautorized");
        }
        Optional<UserEntity> userEntity = userRepository.findById(tokenRepository.findByValue(token).get().getUserid());
        if(userEntity.isEmpty()){
            throw new AppException(401, "Unautorized");
        }
        List<KeyEntity> keyEntities = keyRepository.findAll();
        KeysList keyDTOS = new KeysList();
        keyEntities.forEach(keyEntity -> {
            keyDTOS.addToList(new KeyDTO(keyEntity));
        });
        return keyDTOS;
    }


    @SneakyThrows
    @Override
    public StatusCode transitKey(UUID userid, String tokenValue, UUID keyid) {
        if(tokenRepository.findByValue(tokenValue).isEmpty()){
            throw new AppException(401, "Unauthorization");
        }

        if(userRepository.findById(userid).isEmpty() || userRepository.findById(tokenRepository.findByValue(tokenValue).get().getUserid()).isEmpty()){
            throw new AppException(404, "User not found");
        }
        UserEntity userFrom = userRepository.findById(tokenRepository.findByValue(tokenValue).get().getUserid()).get();
        UserEntity userTo = userRepository.findById(userid).get();
        if(keyRepository.findById(keyid).isEmpty()){
            throw new AppException(404, "Ключ не найден");
        }
        KeyEntity keyEntity = keyRepository.findById(keyid).get();
        UserKeyEntity userKeyEntity = new UserKeyEntity(userFrom, userTo, keyEntity);
        userKeyRepository.save(userKeyEntity);
        return new StatusCode(200, "Заявка на передачу создана.");
    }

    @SneakyThrows
    @Override
    public List<UserKeyDTO> notifications(String tokenValue) {
        if(tokenRepository.findByValue(tokenValue).isEmpty()){
            throw new AppException(401, "Unauthorized");
        }
        if(userRepository.findById(tokenRepository.findByValue(tokenValue).get().getUserid()).isEmpty()){
            throw new AppException(404, "User not found");
        }
        List<UserKeyEntity> userKeyEntities = userKeyRepository.findAllUserKeyEntityByUserTo(
                userRepository.findById(tokenRepository.findByValue(tokenValue).get().getUserid()).get()
        );
        List<UserKeyDTO> userKeyDTOList = new ArrayList<>();
        userKeyEntities.forEach(userKeyEntity -> {
            userKeyDTOList.add(new UserKeyDTO(userKeyEntity));
        });
        return userKeyDTOList;
    }
}
