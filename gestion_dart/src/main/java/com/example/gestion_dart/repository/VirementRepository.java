package com.example.gestion_dart.repository;

import com.example.gestion_dart.entity.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirementRepository extends JpaRepository<Virement, Long> {
    // Add custom queries if needed
}
