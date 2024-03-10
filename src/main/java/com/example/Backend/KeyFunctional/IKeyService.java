package com.example.Backend.KeyFunctional;


import com.example.Backend.statusCode.StatusCode;

import java.util.List;
import java.util.UUID;

public interface IKeyService {
    KeyDTO createKey(UUID officeId, String tokenvalue, KeyDTO keyDTO);
    KeyDTO editKey(UUID officeId, String tokenvalue, KeyDTO keyDTO);
    StatusCode deleteKey(UUID officeId, String tokenvalue, UUID keyId);

    List<KeyDTO> myKeys(String tokenValue);

    StatusCode transitKey(UUID userid, String tokenValue, UUID keyid);
}
