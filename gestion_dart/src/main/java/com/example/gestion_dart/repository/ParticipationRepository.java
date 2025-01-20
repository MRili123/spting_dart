package com.example.gestion_dart.repository;

import com.example.gestion_dart.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {

    List<Participation> findByUser_Id(Long userId);

    Participation findByDartIdAndUserId(Long dartId, Long userId);

    Participation findBySort(Integer currentTurn);
}
