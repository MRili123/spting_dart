package com.example.gestion_dart.service.imp;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.entity.Request;
import com.example.gestion_dart.repository.RequestRepository;
import com.example.gestion_dart.service.RequestService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RequestServiceImp implements RequestService {

    private RequestRepository requestRepository;

    public RequestServiceImp(RequestRepository requestRepository) {
        super();
        this.requestRepository = requestRepository;
    }
    @Override
    public List<Request> getPendingRequests() {
        return requestRepository.findByAcceptFalse();
    }

    @Override
    public int getCountOfAcceptedRequestsForDart(Dart dart) {
        return requestRepository.countAcceptedRequestsForDart(dart);
    }

    @Override
    public int getMaxParticipantsForDart(Dart dart) {
        Integer maxParticipants = requestRepository.findMaxParticipantsForDart(dart);
        return maxParticipants != null ? maxParticipants : 0;
    }


    @Override
    public List<Request> getAcceptedRequestsForDart(Dart dart) {
        return requestRepository.findAcceptedRequestsForDart(dart);
    }
    public int getMaxNumberForDart(Dart dart) {
        Integer maxNumber = requestRepository.findMaxNumberForDart(dart);
        return maxNumber != null ? maxNumber : 0;
    }


    @Override
    public List<Request> getAllRequest() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> getallRequest() {
        return null;
    }

    @Override
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void deletRequestById(long id) {
requestRepository.deleteById(id);
    }

    @Override
    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }
}
