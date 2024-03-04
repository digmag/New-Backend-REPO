package com.example.Backend.Request;

import com.example.Backend.statusCode.StatusCode;

import java.util.List;

public interface IRequestService {

    List<RequestEntity> getRequestList(String tokenValue);

    StatusCode createNewRequest(String tokenValue, RequestDTO requestDTO);

}
