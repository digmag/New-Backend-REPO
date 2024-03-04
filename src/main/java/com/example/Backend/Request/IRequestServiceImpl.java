package com.example.Backend.Request;

import com.example.Backend.KeyFunctional.KeyEntity;
import com.example.Backend.KeyFunctional.KeyRepository;
import com.example.Backend.OfficeFunctional.OfficeEntity;
import com.example.Backend.OfficeFunctional.OfficeRepository;
import com.example.Backend.TokenFunctional.TokenEntity;
import com.example.Backend.TokenFunctional.TokenRepository;
import com.example.Backend.UserFunctional.UserEntity;
import com.example.Backend.UserFunctional.UserRepository;
import com.example.Backend.statusCode.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IRequestServiceImpl implements IRequestService {

    private final RequestRepository requestRepository;

    private final TokenRepository tokenRepository;

    private final KeyRepository keyRepository;

    private final OfficeRepository officeRepository;

    private final UserRepository userRepository;


    @Override
    @SneakyThrows
    public List<RequestEntity> getRequestList(String tokenValue){

        Optional<TokenEntity> tokenEntity = tokenRepository.findByValue(tokenValue);

        if(tokenEntity.isEmpty()) throw new Exception("Invalid token");

        Optional<List<RequestEntity>> requestEntityList = requestRepository.findRequestEntitiesByUserId(tokenEntity.get().getUserid());

        if(requestEntityList.isEmpty()) throw new Exception("No request");

        return requestEntityList.get();

    }

    @Override
    @SneakyThrows
    public StatusCode createNewRequest(String tokenValue, RequestDTO requestDTO){

        Optional<TokenEntity> tokenEntity = tokenRepository.findByValue(tokenValue);

        if(tokenEntity.isEmpty()) throw new Exception("Invalid token");

        Optional<KeyEntity> key = keyRepository.findKeyEntityByOfficeNameAndOfficeNumber(requestDTO.getOfficeName(),requestDTO.getOfficeNumber());

        if(key.isEmpty()) throw new Exception("Wrong OfficeName or OfficeNumber");

        RequestEntity newRequest = new RequestEntity(tokenEntity.get().getUserid(),requestDTO,key.get());

        requestRepository.save(newRequest);

        return new StatusCode(200,"New request was created");

    }

    @Override
    @SneakyThrows
    public StatusCode updateRequest(String tokenValue, UUID requestId, RequestStatus status){

        Optional<TokenEntity> tokenEntity = tokenRepository.findByValue(tokenValue);
        if(tokenEntity.isEmpty()) throw new Exception("Invalid token");

        UserEntity user = userRepository.getById(tokenEntity.get().getId());

        Optional<OfficeEntity> officeEntity = officeRepository.findByAdministratorId(tokenEntity.get().getUserid());
        if(officeEntity.isEmpty()) throw new Exception("No office?");

        if(requestRepository.findById(requestId).isEmpty()) throw new Exception("No request?");
        RequestEntity requestEntity = requestRepository.findById(requestId).get();

        KeyEntity keyEntity = requestEntity.getKey();
        if(keyEntity.getOfficeId() != officeEntity.get().getId()) throw new Exception("Key doesn't belong to this office");

        if(status == RequestStatus.GIVEN){
            keyEntity.setUser(user);
            keyRepository.save(keyEntity);
        }

        requestEntity.setStatus(status);
        requestRepository.save(requestEntity);

        return new StatusCode(200,"request updated");
    }

}
