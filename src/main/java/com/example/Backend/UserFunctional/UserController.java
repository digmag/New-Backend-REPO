package com.example.Backend.UserFunctional;

import com.example.Backend.Errors.AppException;
import com.example.Backend.TokenFunctional.TokenDTO;
import com.example.Backend.statusCode.StatusCode;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/account")
public class UserController {
    private final IUserService iUserService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        try{
            TokenDTO tokenDTO = iUserService.registration(userDTO);
            return ResponseEntity
                    .ok()
                    .body(tokenDTO);
        }
        catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(new StatusCode(400, "Bad Request"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        try {
            TokenDTO tokenDTO = iUserService.login(userDTO);
            return ResponseEntity.ok().body(tokenDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new StatusCode(400, "Bad Request"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> viewInfo(@RequestHeader(name = "Authorization") String Authorization){
        String tokenValue = Authorization.split(" ")[1];
        try{
            UserViewDTO userViewDTO = iUserService.viewInfo(tokenValue);
            return ResponseEntity
                    .ok()
                    .body(userViewDTO);
        }
        catch (Exception e){
            return ResponseEntity
                    .status(401)
                    .body(new StatusCode(401, "Unauthorized"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization") String Authorization){
        String tokenValue = Authorization.split(" ")[1];
        try{
            return ResponseEntity.ok().body(iUserService.logout(tokenValue));
        }
        catch (Exception e){
            return ResponseEntity.status(401).body(new StatusCode(401, "Unauthorized"));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserByName(@RequestHeader(name = "Authorization") String Authorization, @PathParam("name") String Name){
        String tokenValue = Authorization.split(" ")[1];
        return ResponseEntity.ok().body(iUserService.Allusers(tokenValue, Name));
    }
}
