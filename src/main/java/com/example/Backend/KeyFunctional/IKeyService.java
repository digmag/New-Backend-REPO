package com.example.Backend.KeyFunctional;


import java.util.UUID;

public interface IKeyService {
    KeyDTO createKey(UUID officeId, String tokenvalue, KeyDTO keyDTO);
    KeyDTO editKey(UUID officeId, String tokenvalue, KeyDTO keyDTO);
}
