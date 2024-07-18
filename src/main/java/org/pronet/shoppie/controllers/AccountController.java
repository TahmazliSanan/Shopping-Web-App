package org.pronet.shoppie.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.UserService;
import org.pronet.shoppie.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.UUID;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountUtils accountUtils;

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userService.getUserByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/sign-up-view")
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
            return "redirect:/sign-up-view";
        } else {
            if (!(userEntity.getPassword().equals(userEntity.getConfirmPassword()))) {
                session.setAttribute("errorMessage", "Passwords don't match!");
                return "redirect:/sign-up-view";
            } else {
                UserEntity savedUser = userService.signUp(userEntity, file);
                if (!ObjectUtils.isEmpty(savedUser)) {
                    session.setAttribute("successMessage", "Registration is completed successfully!");
                } else {
                    session.setAttribute("errorMessage", "Registration is not completed!");
                }
            }
        }
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "account/sign-in";
    }

    @GetMapping("/forgot-password-view")
    public String forgotPasswordPage() {
        return "account/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email,
            HttpSession session,
            HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        UserEntity foundedUser = userService.getUserByEmail(email);
        if (ObjectUtils.isEmpty(foundedUser)) {
            session.setAttribute("errorMessage", "Invalid email!");
        } else {
            String resetToken = UUID.randomUUID().toString();
            userService.updateUserResetToken(email, resetToken);
            String url = AccountUtils.generateUrl(request) + "/reset-password?token=" + resetToken;
            Boolean sendMail = accountUtils.sendMail(url, email);
            if (sendMail) {
                session.setAttribute("successMessage", "Reset link sent! Please check your email!");
            } else {
                session.setAttribute("errorMessage", "Reset link is not sent!");
            }
        }
        return "redirect:/forgot-password-view";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(
            @RequestParam String token,
            Model model) {
        UserEntity foundedUser = userService.getUserByToken(token);
        if (foundedUser == null) {
            model.addAttribute("message", "Your link is invalid or expired!");
            return "account/message";
        }
        model.addAttribute("token", token);
        return "account/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordPage(
            @RequestParam String token,
            @RequestParam String password,
            Model model) {
        UserEntity foundedUser = userService.getUserByToken(token);
        if (foundedUser == null) {
            model.addAttribute("errorMessage", "Your link is invalid or expired!");
        } else {
            foundedUser.setPassword(passwordEncoder.encode(password));
            foundedUser.setResetToken(null);
            userService.updateUser(foundedUser);
            model.addAttribute("message","Password is changed successfully!");
        }
        return "account/message";
    }
}
