package com.example.gestion_dart.repository;

import com.example.gestion_dart.entity.Request;
import com.example.gestion_dart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.is_admin = false")
    List<User> findUsersWithoutAdmin();


}
