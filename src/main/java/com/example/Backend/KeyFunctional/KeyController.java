package com.example.Backend.KeyFunctional;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
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
    @PostMapping("/{keyid}/transit/{userid}")
    public ResponseEntity<?> keyTo(@PathVariable UUID userid, @RequestHeader(name = "Authorization") String Authorization, @PathVariable UUID keyid){
        String tokenValue = Authorization.split(" ")[1];
        try{
            return ResponseEntity.ok().body(iKeyService.transitKey(userid, tokenValue, keyid));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body(e);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> mykeys(@RequestHeader(name = "Authorization") String Authorization){
        String tokenValue = Authorization.split(" ")[1];
        try {
            return ResponseEntity.ok().body(iKeyService.myKeys(tokenValue));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body(e);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> listKeys(@RequestHeader(name = "Authorization") String Authorization){
        String tokenValue = Authorization.split(" ")[1];
        try {
            return ResponseEntity.ok().body(iKeyService.listOfKeys(tokenValue));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body(e);
        }
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getNote(@RequestHeader(name = "Authorization") String Authorization){
        String tokenValue = Authorization.split(" ")[1];
        try{
            return ResponseEntity.ok().body(iKeyService.notifications(tokenValue));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submit(@RequestHeader(name = "Authorization") String Authorization, @PathVariable UUID id, @PathParam("submit") boolean submit){
        String tokenValue = Authorization.split(" ")[1];
        try{
            return ResponseEntity.ok().body(iKeyService.submit(tokenValue, id, submit));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body(e);
        }
    }
}
