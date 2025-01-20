package com.example.gestion_dart.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "dart")

public class Dart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_dart", nullable = false)
    private String name_dart;





    @Column(name = "periodicity")
    private Integer periodicity;

    @Column(name = "max_partic")
    private Integer max_partic;

    @Column(name = "current_turn")
    private Integer current_turn;

    @Column(name = "price")
    private Float price;
    @Column(name = "disponible")
    private Boolean disponible;


    public Dart() {
        // Default constructor
    }

    public Dart(Long id, String name_dart, LocalDate date_start, LocalDate date_end, Integer periodicity, Integer max_partic, Float price, Boolean disponible, Integer current_turn) {
        this.id = id;
        this.name_dart = name_dart;

        this.periodicity = periodicity;
        this.max_partic = max_partic;
        this.price = price;
        this.disponible = disponible;
        this.current_turn = current_turn;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_dart() {
        return name_dart;
    }

    public void setName_dart(String name_dart) {
        this.name_dart = name_dart;
    }









    public Integer getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }

    public Integer getCurrent_turn() {
        return current_turn;
    }

    public void setCurrent_turn(Integer current_turn) {
        this.current_turn = current_turn;
    }

    public Integer getMax_partic() {
        return max_partic;
    }

    public void setMax_partic(Integer max_partic) {
        this.max_partic = max_partic;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Dart{" +
                "id=" + id +
                ", name_dart='" + name_dart + '\'' +
                ", periodicity=" + periodicity +
                ", max_partic=" + max_partic +
                ", price=" + price +
                ", disponible=" + disponible +
                '}';
    }
}
