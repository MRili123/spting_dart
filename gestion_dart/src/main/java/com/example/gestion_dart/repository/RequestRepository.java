package com.example.gestion_dart.repository;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.entity.Request;
import com.example.gestion_dart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {

    List<Request> findByAcceptFalse();
    @Query("SELECT COALESCE(SUM(r.pourcentage), 0) FROM Request r WHERE r.dart = :dart")
    int sumPourcentageForDart(@Param("dart") Dart dart);
    long countByUser_Id(Long userId);
    List<Request> findByUserId(Long userId);
    @Query("SELECT MAX(r.number) FROM Request r WHERE r.dart = :dart")
    Integer findMaxNumberForDart(@Param("dart") Dart dart);
    boolean existsByUserAndDart(User user, Dart dart);
    @Query("SELECT COUNT(r) FROM Request r WHERE r.dart = :dart AND r.accept = true")
    int countAcceptedRequestsForDart(@Param("dart") Dart dart);
    @Query("SELECT MAX(r.number) FROM Request r WHERE r.dart = :dart")
    Integer findMaxParticipantsForDart(@Param("dart") Dart dart);

    @Query("SELECT r FROM Request r WHERE r.dart = :dart AND r.accept = true")
    List<Request> findAcceptedRequestsForDart(@Param("dart") Dart dart);


}
