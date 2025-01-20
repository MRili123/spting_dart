package com.example.gestion_dart.service.imp;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.entity.Participation;
import com.example.gestion_dart.repository.ParticipationRepository;
import com.example.gestion_dart.service.participationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class participationServiceImp implements participationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Override
    public void saveParticipation(Participation participation) {
        System.out.println("Saving participation: " + participation);
        participationRepository.save(participation);
    }
    @Override
    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    public List<Participation> getParticipantsByDart(Dart dart) {
        List<Participation> allParticipants = getAllParticipations();

        return allParticipants.stream()
                .filter(participation -> participation.getDart().equals(dart))
                .collect(Collectors.toList());
    }
}
