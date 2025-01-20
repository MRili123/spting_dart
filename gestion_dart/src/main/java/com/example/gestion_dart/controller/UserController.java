package com.example.gestion_dart.controller;

import com.example.gestion_dart.entity.Dart;
import com.example.gestion_dart.entity.Participation;
import com.example.gestion_dart.entity.Request;
import com.example.gestion_dart.entity.User;
import com.example.gestion_dart.repository.UserRepository;
import com.example.gestion_dart.service.DartService;
import com.example.gestion_dart.service.RequestService;
import com.example.gestion_dart.service.UserService;
import com.example.gestion_dart.service.imp.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.gestion_dart.service.participationService;

@Controller
public class UserController {
    private DartService dartService;
    private UserService userService;
    private RequestService requestService;

    @Autowired
    private participationService participationService;

    public UserController(DartService dartService, UserService userService, RequestService requestService, participationService participationService) {
        super();
        this.dartService = dartService;
        this.userService = userService;
        this.requestService = requestService;
        this.participationService = participationService;


    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if ("ROLE_ADMIN".equals(auth.getAuthority())) {
                    return "redirect:/admin";
                } else if ("ROLE_USER".equals(auth.getAuthority())) {
                    return "redirect:/user";
                }
            }
        }

        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }


    @GetMapping("/")
    public String dashboard() {
        return "index";
    }

    @GetMapping("/user")
    public String useraffich(Model model, Authentication authentication) {
        if (authentication != null) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("auth", customUserDetails);
        }

        model.addAttribute("darts", dartService.getallDart_with_dis());
        return "user/index";
    }


    @GetMapping("/admin")
    public String listDarts(Model model) {
        model.addAttribute("dart", new Dart()); // Make sure to create a new Dart object
        model.addAttribute("darts", dartService.getallDart());
        return "admin/index";
    }

    @GetMapping("/register")
    public String ShowregisterUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {

        user.setIs_admin(false);

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

    @GetMapping("/usersAdmin")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getallUserWithoutAdmin());
        model.addAttribute("editedUser", new User());
        return "admin/usersAdmin";


    }

    @GetMapping("/requests")
    public String listrequests(Model model) {
        List<Request> pendingRequests = requestService.getPendingRequests();
        model.addAttribute("requests", pendingRequests);
        return "admin/requestsAdmin";
    }

    @GetMapping("/RequestDelet/{id}")
    public String deletrequest(@PathVariable long id) {
        requestService.deletRequestById(id);
        return "redirect:/requests";
    }

    ////////////////////////
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("editedUser") User editedUser) {
        userService.editUser(id, editedUser);
        return "redirect:/usersAdmin";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/usersAdmin";
    }

    @GetMapping("/RequestAccept/{id}")
    public String dacceptrequest(@PathVariable long id) {
        Request request = requestService.getRequestById(id);

        if (request != null) {
            request.setAccept(true);
            Dart dart = request.getDart();
            int requestCount = requestService.getCountOfAcceptedRequestsForDart(dart);

            request.setNumber(requestCount + 1);
            requestService.saveRequest(request);

            if (request.getNumber() != null && request.getNumber() >= requestService.getMaxParticipantsForDart(dart)) {
                if (request.getpourcentage_rester() == 0) {
                    // Insert into Participation
                    // You need to adjust this part based on how you insert participants in your code
                    List<Participation> insertedParticipants = insertParticipants(dart);

                    // Replace sort values in the same Dart
                    replaceSortValues(dart, insertedParticipants);

                    dart.setDisponible(false);
                    dartService.saveDart(dart);


                }


                // Create and save a new Notification with "Accepted" type

            }

            // Other logic
            return "redirect:/requests";
        } else {
            return "redirect:/error";
        }

    }

    private List<Participation> insertParticipants(Dart dart) {
        List<Request> acceptedRequests = requestService.getAcceptedRequestsForDart(dart);
        List<Participation> insertedParticipants = new ArrayList<>();

        if (acceptedRequests != null) {
            // Extract min and max numbers from accepted requests
            int minNumber = acceptedRequests.stream().mapToInt(Request::getNumber).min().orElse(0);
            int maxNumber = acceptedRequests.stream().mapToInt(Request::getNumber).max().orElse(0);

            for (Request acceptedRequest : acceptedRequests) {
                acceptedRequest.setAccept(true);
                requestService.saveRequest(acceptedRequest);

                Float amountPaid = calculateDartPrice(dart);

                // Generate a random sort value within the range of min and max numbers
                int sortValue = generateRandomSortValue(minNumber, maxNumber);

                Participation participation = new Participation(
                        acceptedRequest.getUser(),
                        dart,
                        amountPaid,
                        sortValue,
                        acceptedRequest.getPourcentage()
                );

                participationService.saveParticipation(participation);
                insertedParticipants.add(participation);
            }
        }

        return insertedParticipants;
    }

    private int generateRandomSortValue(int minNumber, int maxNumber) {
        Random random = new Random();
        return random.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }


    private void replaceSortValues(Dart dart, List<Participation> insertedParticipants) {
        List<Participation> existingParticipants = participationService.getParticipantsByDart(dart);

        // Shuffle the insertedParticipants again for better randomness
        Collections.shuffle(insertedParticipants);

        // Extract min and max numbers from existingParticipants
        int minNumber = existingParticipants.stream().mapToInt(Participation::getSort).min().orElse(0);
        int maxNumber = existingParticipants.stream().mapToInt(Participation::getSort).max().orElse(0);

        // Ensure that maxNumber is at least as large as the number of participants
        maxNumber = Math.max(maxNumber, existingParticipants.size());

        Set<Integer> usedSortValues = new HashSet<>();

        for (Participation newParticipant : insertedParticipants) {
            // Ensure uniqueness of sort values
            int newSortValue = getUniqueSortValue(usedSortValues, minNumber, maxNumber);
            newParticipant.setSort(newSortValue);

            // Save the updated Participation object
            participationService.saveParticipation(newParticipant);


            usedSortValues.add(newSortValue);
        }
    }

    private int getUniqueSortValue(Set<Integer> usedSortValues, int minNumber, int maxNumber) {
        Random random = new Random();
        int newSortValue;

        do {
            newSortValue = random.nextInt((maxNumber - minNumber) + 1) + minNumber;
        } while (usedSortValues.contains(newSortValue));

        return newSortValue;
    }

    private Float calculateDartPrice(Dart dart) {
        // Replace this with your actual logic to calculate the price for the dart
        // For example, you might have a field in Dart entity representing the price
        return dart.getPrice();
    }


}





