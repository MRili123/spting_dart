package com.example.gestion_dart.service;

import com.example.gestion_dart.entity.Dart;

import java.util.List;

public interface DartService {
    List<Dart> getallDart();

    Dart saveDart(Dart dart);

    List<Dart> getallDart_with_dis();
}
