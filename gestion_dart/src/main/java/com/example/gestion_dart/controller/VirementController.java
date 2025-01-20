package com.example.gestion_dart.controller;

import com.example.gestion_dart.entity.Virement;
import com.example.gestion_dart.service.imp.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VirementController{

    @Autowired
    private VirementService virementService;

    @PostMapping("/processVirement")
    public String processVirement(@RequestParam Long dartId) {
        return virementService.processVirement(dartId);
    }

    @PostMapping("/userPay")
    public String userPay(@RequestParam Long dartId, @RequestParam Long userId) {
        // Call virementService.userPay with dartId and userId
        virementService.userPay(dartId, userId);

        // Redirect to the appropriate page after payment
        return "redirect:/mydartUser?error=Success Pay"; // Replace with your desired redirect path
    }


}
