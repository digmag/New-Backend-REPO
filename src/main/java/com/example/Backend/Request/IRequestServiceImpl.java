package com.example.Backend.Request;

import com.example.Backend.Errors.AppException;
import com.example.Backend.KeyFunctional.KeyEntity;
import com.example.Backend.KeyFunctional.KeyRepository;
import com.example.Backend.OfficeFunctional.OfficeEntity;
import com.example.Backend.OfficeFunctional.OfficeRepository;
import com.example.Backend.Relations.UserToOfficeRepository;
import com.example.Backend.TokenFunctional.TokenEntity;
import com.example.Backend.TokenFunctional.TokenRepository;
import com.example.Backend.UserFunctional.UserEntity;
import com.example.Backend.UserFunctional.UserRepository;
import com.example.Backend.statusCode.StatusCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.catalina.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IRequestServiceImpl implements IRequestService {

    private final RequestRepository requestRepository;

    private final TokenRepository tokenRepository;

    private final KeyRepository keyRepository;

    private final OfficeRepository officeRepository;

    private final UserRepository userRepository;

    private final UserToOfficeRepository userToOfficeRepository;


    @Override
    @SneakyThrows
    public RequestList getMyRequestList(String tokenValue){

        Optional<TokenEntity> tokenEntity = tokenRepository.findByValue(tokenValue);

        if(tokenEntity.isEmpty()) throw new Exception("Invalid token");

        Optional<List<RequestEntity>> requestEntityList = requestRepository.findRequestEntitiesByUserId(tokenEntity.get().getUserid());

        if(requestEntityList.isEmpty()) throw new Exception("No request");

        RequestList list = new RequestList();

        for(RequestEntity re : requestEntityList.get()){
            list.addToList(new RequestDTO(re));
        }

        return list;

    }


    @Override
    @SneakyThrows
    public StatusCode createNewRequest(String tokenValue, RequestDTO requestDTO){

        Optional<TokenEntity> tokenEntity = tokenRepository.findByValue(tokenValue);

        if(tokenEntity.isEmpty()) throw new Exception("Invalid token");

        Optional<KeyEntity> key = keyRepository.findKeyEntityByOfficeIdAndKeyId(requestDTO.getOfficeId(), requestDTO.getKeyId());

        if(key.isEmpty()) throw new Exception("Wrong OfficeName or OfficeNumber");

        RequestEntity newRequest = new RequestEntity(tokenEntity.get().getUserid(),requestDTO,key.get());

        //проверка на учителя
        if(userToOfficeRepository.findByUserIdAndOfficeId(tokenEntity.get().getUserid(),requestDTO.getOfficeId()).get().getRole().equals("Teacher")){
            if(requestRepository.findAllByKeyAndRequestedDateTimeAndStatus(key.get(),requestDTO.getRequestedDateTime(),RequestStatus.APPROVED).isEmpty()
                    && requestRepository.findAllByKeyAndRequestedDateTimeAndStatus(key.get(),requestDTO.getRequestedDateTime(),RequestStatus.GIVEN).isEmpty()){
                newRequest.setStatus(RequestStatus.APPROVED);
            } else {
                boolean flag = false;
                for (RequestEntity e : requestRepository.findAllByKeyAndRequestedDateTimeAndStatus(key.get(), requestDTO.getRequestedDateTime(), RequestStatus.APPROVED).get()) {
                    if (userToOfficeRepository.findByUserIdAndOfficeId(e.getUserId(), requestDTO.getOfficeId()).get().getRole().equals("Teacher")) {
                        flag=true;
                    }
                    if (flag) {
                        break;
                    }
                }
                if(!flag){
                    for (RequestEntity e : requestRepository.findAllByKeyAndRequestedDateTimeAndStatus(key.get(), requestDTO.getRequestedDateTime(), RequestStatus.GIVEN).get()) {
                        if (userToOfficeRepository.findByUserIdAndOfficeId(e.getUserId(), requestDTO.getOfficeId()).get().getRole().equals("Teacher")) {
                            flag=true;
                        }
                        if (flag) {
                            break;
                        }
                    }
                }
                if(!flag){
                    newRequest.setStatus(RequestStatus.APPROVED);
                }
            }
        } // проверка на занятость ключа учителем
        else {
            boolean flag = false;
            for (RequestEntity e : requestRepository.findAllByKeyAndRequestedDateTimeAndStatus(key.get(), requestDTO.getRequestedDateTime(), RequestStatus.APPROVED).get()) {
                if (userToOfficeRepository.findByUserIdAndOfficeId(e.getUserId(), requestDTO.getOfficeId()).get().getRole().equals("Teacher")) {
                    flag=true;
                    newRequest.setStatus(RequestStatus.DECLINED);
                }
                if (flag) {
                    break;
                }
            }
        }

        requestRepository.save(newRequest);

        return new StatusCode(200,"New request was created");

    }

    @Override
    @SneakyThrows
    public StatusCode updateRequest(String tokenValue, UUID requestId, RequestStatus status){

        Optional<TokenEntity> tokenEntity = tokenRepository.findByValue(tokenValue);
        if(tokenEntity.isEmpty()) throw new AppException(401, "Unauthorized");

        if(userRepository.findById(tokenEntity.get().getUserid()).isEmpty()){
            throw new AppException(401, "Unauthorized");
        }
        UserEntity user = userRepository.findById(tokenEntity.get().getUserid()).get();

        Optional<OfficeEntity> officeEntity = officeRepository.findByAdministratorId(tokenEntity.get().getUserid());//сделать так, чтобы не только админ мог изменять

        if(officeEntity.isEmpty()) throw new AppException(403, "Офис не найден");
        if(userToOfficeRepository.findByUserIdAndOfficeId(user.getId(), officeEntity.get().getId()).isEmpty()){
            throw new AppException(403, "Пользователь не принадлежит к офису");
        }
        var Yuser = userToOfficeRepository.findByUserIdAndOfficeId(user.getId(), officeEntity.get().getId()).get();
        if(Yuser.getRole().equals("Student") ||
                Yuser.getRole().equals("Teacher")){
            throw new AppException(403, "Пользователь не является работником офиса");
        }

        if(requestRepository.findById(requestId).isEmpty()) throw new AppException(400, "Данная заявка не найдена");
        RequestEntity requestEntity = requestRepository.findById(requestId).get();

        KeyEntity keyEntity = requestEntity.getKey();
        if(!keyEntity.getOfficeId().equals(officeEntity.get().getId())) throw new Exception("Key doesn't belong to this office");

        if(status == RequestStatus.GIVEN){
            UserEntity userEntity = userRepository.findById(
                    requestEntity.getUserId()
            ).get();
            keyEntity.setUser(userEntity;
            keyRepository.save(keyEntity);
        }

        requestEntity.setStatus(status);
        requestRepository.save(requestEntity);

        return new StatusCode(200,"request updated");//добавить userViewDTO
    }
    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void deletePastRequest(){
        Date NowDate = new Date();
        List<RequestEntity> requests = requestRepository.findAll();
        requests.forEach(request ->{
            if(NowDate.after(request.getRequestedDateTime()) && (request.getStatus() == RequestStatus.DECLINED
                                                             || request.getStatus() == RequestStatus.EXPIRED
                                                             || request.getStatus() != RequestStatus.IN_PROCESS)){
                requestRepository.deleteById(request.getId());
            }
        });
    }
}
