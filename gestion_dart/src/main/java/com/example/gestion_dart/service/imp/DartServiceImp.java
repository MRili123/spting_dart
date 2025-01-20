package com.example.gestion_dart.service.imp;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.repository.DartRepository;
import com.example.gestion_dart.service.DartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DartServiceImp implements DartService {
    private DartRepository dartRepository;

    public DartServiceImp(DartRepository dartRepository) {
        super();
        this.dartRepository = dartRepository;
    }

    @Override
    public List<Dart> getallDart() {
        return dartRepository.findAll();
    }

    @Override
    public Dart saveDart(Dart dart) {
        return dartRepository.save(dart);
    }
@Override
    public List<Dart> getallDart_with_dis() {
        List<Dart> allDarts = dartRepository.findAll();


        List<Dart> availableDarts = allDarts.stream()
                .filter(Dart::getDisponible)
                .collect(Collectors.toList());

        return availableDarts;
    }


}
