package com.example.Backend.OfficeFunctional;

import com.example.Backend.Errors.AppException;
import com.example.Backend.UserFunctional.UserAppointmentDTO;
import com.example.Backend.UserFunctional.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/office")
public class OfficeController {
    private final IOfficeService iOfficeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OfficeCreateDTO officeCreateDTO, @RequestHeader(name = "Authorization") String Authorization){
        try{
            return ResponseEntity
                    .ok()
                    .body(
                            iOfficeService.createOffice(
                                    officeCreateDTO,
                                    Authorization.split(" ")[1]
                            )
                    );
        }
        catch (Exception e){
            return ResponseEntity
                    .status(401)
                    .body(e);
        }
    }

    @PostMapping("/{id}/appointment")
    public ResponseEntity<?> appointment(@RequestHeader(name = "Authorization") String Authorization,
                                         @RequestBody UserAppointmentDTO userAppointmentDTO,
                                         @PathVariable UUID id){
        String tokenValue = Authorization.split(" ")[1];
        try{
            return ResponseEntity
                    .ok()
                    .body(iOfficeService.appointment(id, userAppointmentDTO, tokenValue));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(401)
                    .body(e);
        }
    }
    /*@GetMapping("/{id}/getall")
    public ResponseEntity<?> getAll(@RequestHeader(name = "Authorization") String Authorization, @PathVariable UUID id){
        String tokenValue = Authorization.split(" ")[1];
        try {

            return ResponseEntity.ok(iOfficeService.viewAll(tokenValue, id));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(400).body(e);
        }
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> getinfo(@RequestHeader(name = "Authorization") String Authorization, @PathVariable UUID id){
        String tokenValue = Authorization.split(" ")[1];
        try {
            return ResponseEntity
                    .ok()
                    .body(iOfficeService.allinfo(tokenValue, id));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(400)
                    .body(e);
        }
    }
}
