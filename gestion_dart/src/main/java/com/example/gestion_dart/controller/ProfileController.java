package com.example.gestion_dart.controller;

import com.example.gestion_dart.entity.User;
import com.example.gestion_dart.service.UserService;
import com.example.gestion_dart.service.imp.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller

public class ProfileController {
    private UserService userService ;
    private Object MediaType;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profileAdmin")
    public String profileadmin(Model model, Authentication authentication) {
            if (authentication != null) {
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                model.addAttribute("editedUser", new User());
                model.addAttribute("auth", customUserDetails);
            }
        return "admin/profile_admin";
    }
    @GetMapping("/profileUser")
    public String profileuser(Model model, Authentication authentication) {
        if (authentication != null) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("editedUser", new User());
            model.addAttribute("auth", customUserDetails);
        }
        return "user/profile_user";
    }



// ...

    @PostMapping("/editprofileAdmin")
    public String editprofileadmin(@ModelAttribute("editedUser") User editedUser) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            Long userId = ((CustomUserDetails) principal).getId();
            userService.editUser(userId, editedUser);
        }

        return "redirect:/logout";
    }
    @PostMapping("/editprofileUser")
    public String editprofileuser(@ModelAttribute("editedUser") User editedUser) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            Long userId = ((CustomUserDetails) principal).getId();
            userService.editUser(userId, editedUser);
        }

        return "redirect:/logout";
    }






}
