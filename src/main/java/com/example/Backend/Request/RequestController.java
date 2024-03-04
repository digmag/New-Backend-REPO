package com.example.Backend.Request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final IRequestService iRequestService;

    @GetMapping("/list")
    public ResponseEntity getRequestList(@RequestHeader(name = "Authorization") String Authorization){
        String tokenvalue = Authorization.split(" ")[1];
        try{
            return ResponseEntity
                    .ok()
                    .body(iRequestService.getRequestList(tokenvalue));
        }
        catch (Exception e){
            return  ResponseEntity
                    .badRequest()
                    .body(e);
        }

    }

    @PostMapping("/create")
    public ResponseEntity createRequestList(@RequestHeader(name = "Authorization") String Authorization, @RequestBody RequestDTO body){
        String tokenvalue = Authorization.split(" ")[1];
        try{
            return ResponseEntity
                    .ok()
                    .body(iRequestService.createNewRequest(tokenvalue,body));
        }
        catch (Exception e){
            return  ResponseEntity
                    .badRequest()
                    .body(e);
        }

    }

}
