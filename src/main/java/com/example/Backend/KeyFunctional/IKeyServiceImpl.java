package com.example.Backend.KeyFunctional;

import com.example.Backend.Errors.AppException;
import com.example.Backend.Relations.UserToOffice;
import com.example.Backend.Relations.UserToOfficeRepository;
import com.example.Backend.TokenFunctional.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IKeyServiceImpl implements IKeyService {
    private final KeyRepository keyRepository;
    private final UserToOfficeRepository userToOfficeRepository;
    private final TokenRepository tokenRepository;
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
        if(!admin.getRole().equals("Admin")){
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
        if(!admin.getRole().equals("Admin")){
            throw new AppException(403, "Невозможно создать ключ");
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
}
