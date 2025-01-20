package com.example.gestion_dart.controller;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.entity.Participation;
import com.example.gestion_dart.entity.Request;
import com.example.gestion_dart.entity.User;
import com.example.gestion_dart.repository.DartRepository;
import com.example.gestion_dart.repository.ParticipationRepository;
import com.example.gestion_dart.repository.RequestRepository;
import com.example.gestion_dart.repository.UserRepository;
import com.example.gestion_dart.service.UserService;
import com.example.gestion_dart.service.imp.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ParticipationController {
    @Autowired
    private UserService userService ;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DartRepository dartRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    public ParticipationController(UserService userService) {
        this.userService = userService;

    }


    @GetMapping("/les_par")
    public String sss(Model model) {
        // Get all participations from the repository
        List<Participation> allParticipations = participationRepository.findAll();

        // Add the list of participations as an attribute to the model
        model.addAttribute("allParticipations", allParticipations);

        // Return the view name
        return "admin/participation";
    }

    @GetMapping("/mydartUser")
    public String mydarets(Model model, Authentication authentication) {
        if (authentication != null) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String username = customUserDetails.getUsername();

            // Assuming you have a method to get the user by username
            User authUser = userRepository.findByUsername(username);

            if (authUser != null) {
                // Get the user's ID
                Long userId = authUser.getId();

                List<Participation> userParticipations = participationRepository.findByUser_Id(userId);

                // Add the count and participations to the model
                model.addAttribute("userParticipations", userParticipations);


                return "user/participation_user";
            }
        }
        return "redirect:/errorPage";
    }



    @PostMapping("/participation")
    public String saveRequest(@RequestParam("id") Long id,
                              @RequestParam("id_user") Long userId,
                              @RequestParam("pourcentage") Integer pourcentage) {

        // Check if pourcentage is valid (50% or 100%)
        if (pourcentage != 50 && pourcentage != 100) {
            // Redirect with an error message
            return "redirect:/user?error=InvalidPourcentage";
        }

        User user = userRepository.findById(userId).orElse(null);
        Dart dart = dartRepository.findById(id).orElse(null);

        if (user != null && dart != null) {
            // Check if the user has already participated in the dart
            if (!requestRepository.existsByUserAndDart(user, dart)) {
                // Calculate numb_place based on dart.max_partic and pourcentage
                int maxPartic = dart.getMax_partic();
                int pourcentageRester = maxPartic * 100 - requestRepository.sumPourcentageForDart(dart) - pourcentage;
                int p = pourcentageRester + pourcentage;

                if (p == 50 && pourcentage == 100) {
                    // Redirect to error with message dart plein
                    return "redirect:/user?error=Only 50%  Left";
                }
                if (pourcentageRester >= 0) {
                    Request request = new Request();
                    request.setUser(user);
                    request.setDart(dart);
                    request.setAccept(false);
                    request.setPourcentage(pourcentage);
                    request.setpourcentage_rester(pourcentageRester);

                    requestRepository.save(request);

                } else {
                    // Redirect to error with message Only50PercentLeft

                    return "redirect:/user?error=DartPlein";
                }
            } else {
                // User has already participated in the dart, handle accordingly
                return "redirect:/user?error=you_are_AlreadyParticipated";
            }
        } else {
            return "redirect:/user?error=InvalidRequest";
        }

        return "redirect:/user";
    }


}







