package com.example.gestion_dart.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "daret_operation_id")
    private Dart dart;

    @Column(name = "accept")
    private boolean accept;
    @Column(name = "number")
    private Integer number;

    @Column(name = "pourcentage")
    private Integer pourcentage;
    @Column(name = "pourcentage_rester")
    private Integer pourcentage_rester;
    public  Request(){

    }

    public Request(Long id, User user, Dart dart, boolean accept,Integer number ,Integer pourcentage,Integer numb_place) {
        this.id = id;
        this.user = user;
        this.dart = dart;
        this.accept = accept;
        this.number = number ;
        this.pourcentage = pourcentage ;
        this.pourcentage_rester =  pourcentage_rester ;
    }
    public Request(Long id, User user, Dart dart, boolean accept, Integer pourcentage,Integer pourcentage_rester) {
        this(id, user, dart, accept, 0,pourcentage ,pourcentage_rester); // Or set it to any default value you prefer
    }

    public static void save(Request request) {
    }

    public Integer getpourcentage_rester() {
        return pourcentage_rester;
    }

    public void setpourcentage_rester(Integer pourcentage_rester) {
        this.pourcentage_rester = pourcentage_rester;
    }

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

    public Integer  getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isAccept() {
        return accept;
    }

    public Integer getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
