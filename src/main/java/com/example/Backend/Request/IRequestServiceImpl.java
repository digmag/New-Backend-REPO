package com.example.Backend.Request;

import com.example.Backend.KeyFunctional.KeyEntity;
import com.example.Backend.KeyFunctional.KeyRepository;
import com.example.Backend.TokenFunctional.TokenEntity;
import com.example.Backend.TokenFunctional.TokenRepository;
import com.example.Backend.statusCode.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IRequestServiceImpl implements IRequestService {

    private final RequestRepository requestRepository;

    private final TokenRepository tokenRepository;

    private final KeyRepository keyRepository;


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

}
