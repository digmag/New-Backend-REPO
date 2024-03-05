package com.example.Backend.KeyFunctional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keys")
public class KeyController {
    private final IKeyService iKeyService;

    @PostMapping("/{officeId}/create")
    public ResponseEntity<?> createKey(@PathVariable UUID officeId, @RequestHeader(name = "Authorization") String Authorization, @RequestBody KeyDTO keyDTO){
        String tokenvalue = Authorization.split(" ")[1];
        try{
            return ResponseEntity
                    .ok()
                    .body(iKeyService.createKey(officeId, tokenvalue, keyDTO));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{officeId}/edit")
    public ResponseEntity<?> editKey(@PathVariable UUID officeId, @RequestHeader(name = "Authorization") String Authorization, @RequestBody KeyDTO keyDTO){
        String tokenvalue = Authorization.split(" ")[1];
        try{
            return ResponseEntity
                    .ok()
                    .body(iKeyService.editKey(officeId, tokenvalue, keyDTO));
        }
        catch (Exception e){
            return  ResponseEntity
                    .badRequest()
                    .body(e);
        }
    }

    @DeleteMapping("/{officeId}/delete/{keyId}")
    public ResponseEntity<?> deleteKey(@PathVariable UUID officeId, @RequestHeader(name = "Authorization") String Authorization, @PathVariable UUID keyId){
        String tokenValue = Authorization.split(" ")[1];
        try{
            return ResponseEntity
                    .ok()
                    .body(iKeyService.deleteKey(officeId,tokenValue,keyId));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(500)
                    .body(e);
        }
    }
}