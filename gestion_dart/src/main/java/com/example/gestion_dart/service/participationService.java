package com.example.gestion_dart.service;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.entity.Participation;

import java.util.List;

public interface participationService {
    void saveParticipation(Participation participation);

    List<Participation> getAllParticipations();
    List<Participation> getParticipantsByDart(Dart dart);
}
