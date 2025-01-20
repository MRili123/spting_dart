package com.example.gestion_dart.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")  // name of the foreign key column in the users table
    private User user;

    @ManyToOne
    @JoinColumn(name = "daret_operation_id")  // name of the foreign key column in the dart table
    private Dart dart;

    @Column(name = "amount_paid")
    private Float amount_paid;
    @Column(name = "sort")
    private Integer sort;
    @Column(name = "pourcentage")
    private Integer pourcentage;

    @Column(name = "is_paid")
    private Boolean is_paid;

    @Column(name = "a_paye")
    private Boolean a_paye;

    public Participation() {
        // Default constructor
    }

    // Constructor without id field
    public Participation(User user, Dart dart, Float amount_paid,Integer sort,Integer pourcentage) {
        this.user = user;
        this.dart = dart;
        this.amount_paid = amount_paid;
        this.sort = sort;
        this.pourcentage = pourcentage ;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Dart getDart() {
        return dart;
    }

    public void setDart(Dart dart) {
        this.dart = dart;
    }

    public Float getAmount_paid() {
        return amount_paid;
    }

    public Integer getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
    }

    public void setAmount_paid(Float amount_paid) {
        this.amount_paid = amount_paid;
    }

    public boolean getA_paye() {
        return a_paye != null ? a_paye.booleanValue() : false;
    }

    public void setA_paye(Boolean a_paye) {
        this.a_paye = a_paye;
    }

    public Boolean getIs_paid() {
        return is_paid != null ? is_paid : false;
    }

    public void setIs_paid(Boolean is_paid) {
        this.is_paid = is_paid;
    }
}
