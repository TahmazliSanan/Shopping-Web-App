package org.pronet.shoppie.controllers;

import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userService.getUserByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(
            @ModelAttribute UserEntity userEntity,
            @RequestParam("file") MultipartFile file,
            HttpSession session) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        userEntity.setProfileImageName(imageName);
        Boolean isUserExist = userService.existsUserByEmail(userEntity.getEmail());
        if (isUserExist) {
            session.setAttribute("errorMessage", "User is already exist!");
            return "redirect:/sign-up";
        } else {
            UserEntity savedUser = userService.signUp(userEntity, file);
            if (!ObjectUtils.isEmpty(savedUser)) {
                session.setAttribute("successMessage", "Registration is completed successfully!");
            } else {
                session.setAttribute("errorMessage", "Registration is not completed!");
            }
        }
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "account/sign-in";
    }
}
