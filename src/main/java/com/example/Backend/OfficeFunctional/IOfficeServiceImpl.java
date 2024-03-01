package com.example.Backend.OfficeFunctional;

import com.example.Backend.Errors.AppException;
import com.example.Backend.KeyFunctional.KeyDTO;
import com.example.Backend.KeyFunctional.KeyEntity;
import com.example.Backend.KeyFunctional.KeyRepository;
import com.example.Backend.Relations.UserToOffice;
import com.example.Backend.Relations.UserToOfficeRepository;
import com.example.Backend.TokenFunctional.TokenEntity;
import com.example.Backend.TokenFunctional.TokenRepository;
import com.example.Backend.UserFunctional.*;
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
public class IOfficeServiceImpl implements IOfficeService {
    private final OfficeRepository officeRepository;
    private final TokenRepository tokenRepository;
    private final UserToOfficeRepository userToOfficeRepository;
    private final UserRepository userRepository;
    private final KeyRepository keyRepository;

    @SneakyThrows
    @Override
    public OfficeDTO createOffice(OfficeCreateDTO officeCreateDTO, String tokenValue) {
        Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByValue(tokenValue);
        if(tokenEntityOptional.isEmpty()){
            throw new AppException(401, "Unauthorized");
        }
        OfficeEntity office = new OfficeEntity(officeCreateDTO.getName(), tokenEntityOptional.get().getUserid());
        if(officeRepository.findByAdministratorId(tokenEntityOptional.get().getUserid()).isPresent()){
            throw new AppException(400, "Пользователь не может иметь больше одного офиса");
        }
        officeRepository.save(office);
        userToOfficeRepository.save(new UserToOffice(
                userRepository.findById(tokenEntityOptional.get().getUserid()).get(),
                officeRepository.findByAdministratorId(tokenEntityOptional.get().getUserid()).get(),
                "Administrator"));
        return new OfficeDTO(officeRepository.findByAdministratorId(tokenEntityOptional.get().getUserid()).get());
    }

    @SneakyThrows
    @Override
    public StatusCode appointment(UUID Officeid, UserAppointmentDTO userAppointmentDTO, String tokenValue) {
        Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByValue(tokenValue);
        if(tokenEntityOptional.isEmpty()){
            throw new AppException(401, "Unauthorized.");
        }
        if(officeRepository.findById(Officeid).isEmpty()){
            throw new AppException(400, "Офис не найден.");
        }
        Optional<UserToOffice> userEntityOptional = userToOfficeRepository.findByUserId(tokenEntityOptional.get().getUserid());
        if(userEntityOptional.isPresent() && (userEntityOptional.get().getRole().equals("Administrator") ||
                userEntityOptional.get().getRole().equals("Office employee"))){
            if(userRepository.findById(userAppointmentDTO.getId()).isEmpty()) {
                throw new AppException(400, "Пользователь не найден");
            }
            if(userAppointmentDTO.getRole() == null || userAppointmentDTO.getRole().equals("")){
                throw new AppException(400, "Поле роль не может быть пустым");
            }
            //тут нужно сделать проверку не был ли пользователь добавлен раньше
            if(userToOfficeRepository.findByUserIdAndOfficeId(userAppointmentDTO.getId(),Officeid).isPresent()){
                throw new AppException(400, "Пользователь уже добавлен в данный офис");
            }
            UserEntity newUserEntity = userRepository.findById(userAppointmentDTO.getId()).get();
            UserToOffice userToOffice = new UserToOffice(newUserEntity, officeRepository.findById(Officeid).get(), userAppointmentDTO.getRole());
            userToOfficeRepository.save(userToOffice);
            return new StatusCode(200, "Пользователь добавлен.");
        }
        else{
            throw new AppException(403, "У вас нет прав, чтобы добавлять участников.");
        }
    }

    @SneakyThrows
    @Override
    public OfficeUsers viewAll(String tokenValue, UUID officeid) {

        var users = userToOfficeRepository.findAllByOfficeId(officeid);
        if (users.isEmpty()){
            throw new AppException(404, "Пользователи не найдены");
        }
        OfficeUsers userOfficeDTOS = new OfficeUsers();
        users.get().forEach(user->{
            userOfficeDTOS.getUsers().add(new UserViewDTO(userRepository.findById(user.getUser().getId()).get(), user.getRole()));
        });
        return userOfficeDTOS;
    }

    @SneakyThrows
    @Override
    public OfficeAllInfoDTO allinfo(String tokenValue, UUID officeid) {
        Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByValue(tokenValue);
        if(tokenEntityOptional.isEmpty()){
            throw new AppException(401, "Unauthorized.");
        }
        if(officeRepository.findById(officeid).isEmpty()){
            throw new AppException(400, "Офис не найден.");
        }
        OfficeAllinfoModel officeAllinfoModel = new OfficeAllinfoModel(officeRepository.findById(officeid).get());
        if(keyRepository.findByOfficeId(officeid).isPresent()) {
            keyRepository.findByOfficeId(officeid).get().forEach(keyEntity -> {
                officeAllinfoModel.addKey(new KeyDTO(keyEntity));
            });
        }
        if(userToOfficeRepository.findAllByOfficeId(officeid).isPresent()) {
            userToOfficeRepository.findAllByOfficeId(officeid).get().forEach(userToOffice -> {
                officeAllinfoModel.addUser(new UserOfficeDTO(userToOffice));
            });
        }
        return new OfficeAllInfoDTO(officeAllinfoModel);
    }
}
