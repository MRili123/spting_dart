package com.example.gestion_dart.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "virement")
public class Virement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")  // name of the foreign key column in the users table
    private User user;

    @ManyToOne
    @JoinColumn(name = "dart_id")  // name of the foreign key column in the users table
    private Dart dart;




    public Virement() {
        // Default constructor
    }

    // Constructor without id field
    public Virement(User user, Dart dart) {
        this.user = user;
        this.dart = dart;

    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dart getDart() {
        return dart;
    }

    public void setDart(Dart dart) {
        this.dart = dart;
    }

}
