package com.example.Backend.Request;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final IRequestService iRequestService;

    @GetMapping("/list")
    public ResponseEntity getList(@RequestHeader(name = "Authorization") String Authorization){
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
    public ResponseEntity createRequest(@RequestHeader(name = "Authorization") String Authorization, @RequestBody RequestDTO body){
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

    @PutMapping("/update")
    public ResponseEntity updateRequest(@RequestHeader(name = "Authorization") String Authorization, @PathParam("requestId") UUID requestId, @PathParam("status") RequestStatus status){
        String tokenvalue = Authorization.split(" ")[1];
        try{
            return ResponseEntity
                    .ok()
                    .body(iRequestService.updateRequest(tokenvalue,requestId,status));
        }
        catch (Exception e){
            return  ResponseEntity
                    .badRequest()
                    .body(e);
        }
    }

}
