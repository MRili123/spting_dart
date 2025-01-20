package com.example.gestion_dart.service;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.entity.Request;
import com.example.gestion_dart.entity.User;

import java.util.List;

public interface RequestService {
    List<Request> getAllRequest();

    List<Request> getallRequest();

    Request saveRequest(Request request);
    void deletRequestById(long id );
    Request getRequestById(Long id);
    List<Request> getPendingRequests();
    int getCountOfAcceptedRequestsForDart(Dart dart);
    int getMaxParticipantsForDart(Dart dart);

    List<Request> getAcceptedRequestsForDart(Dart dart);
    int getMaxNumberForDart(Dart dart);

}
