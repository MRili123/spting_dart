package com.example.gestion_dart.controller;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.service.DartService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class DartController {
private DartService dartService;

public DartController(DartService dartService){
super();
this.dartService =dartService;
}
//    @GetMapping("/dartsAdmin")
//    public String listDarts(Model model){
//        model.addAttribute("darts",dartService.getallDart());
//        return "admin/dartcreate";
//    }
    @GetMapping("/error")
    public String Error(){

        return "error";
    }
    @GetMapping("/darts/new")
    public String createDartForm(Model model){
        Dart dart =new Dart();
        model.addAttribute("dart",dart);
        return "admin/create_dart";
    }

    @PostMapping("/AddDarts")
    public String saveDart(@ModelAttribute("dart") Dart dart, RedirectAttributes redirectAttributes) {
        try {
            if (dart == null || dart.getName_dart().isEmpty() || dart.getPeriodicity()==0 || dart.getPrice() ==0 || dart.getMax_partic() ==0) {
                // Dart object is empty or has empty fields
                redirectAttributes.addFlashAttribute("error", "Input fields cannot be empty");
                return "redirect:/admin";
            }
           dart.setCurrent_turn(1);
            dart.setDisponible(true);
            dartService.saveDart(dart);

            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            return "error-page";
        }
    }


    public static class ParticipationController {


    }


}
