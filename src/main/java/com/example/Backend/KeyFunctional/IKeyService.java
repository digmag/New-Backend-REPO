package com.example.Backend.KeyFunctional;


import com.example.Backend.OfficeFunctional.OfficesList;
import com.example.Backend.Relations.UserKeyEntity;
import com.example.Backend.statusCode.StatusCode;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

public interface IKeyService {
    KeyDTO createKey(UUID officeId, String tokenvalue, KeyDTO keyDTO);
    KeyDTO editKey(UUID officeId, String tokenvalue, KeyDTO keyDTO);
    StatusCode deleteKey(UUID officeId, String tokenvalue, UUID keyId);

    List<KeyDTO> myKeys(String tokenValue);

    KeysList listOfKeys(String token);

    StatusCode transitKey(UUID userid, String tokenValue, UUID keyid);

    List<UserKeyDTO> notifications(String tokenValue);

    StatusCode submit(String tokenValue, UUID id, boolean submit);
}
