package com.example.Backend.Request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestList {

    List<RequestDTO> requests = new ArrayList<RequestDTO>();

    public void addToList(RequestDTO requestDTO){
        requests.add(requestDTO);
    }

}
