package com.example.Backend.Request;

import com.example.Backend.statusCode.StatusCode;

import java.util.List;
import java.util.UUID;

public interface IRequestService {

    List<RequestEntity> getRequestList(String tokenValue);

    StatusCode createNewRequest(String tokenValue, RequestDTO requestDTO);

    StatusCode updateRequest(String tokenValue, UUID requestId, RequestStatus status);

    void deletePastRequest();

}
