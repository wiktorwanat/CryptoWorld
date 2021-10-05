/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.HttpModels.JwtResponse;
import com.wwanat.CryptoWorld.HttpModels.LoginRequest;
import com.wwanat.CryptoWorld.HttpModels.MessageResponse;
import com.wwanat.CryptoWorld.HttpModels.SignupRequest;
import com.wwanat.CryptoWorld.Mail.MailService;
import com.wwanat.CryptoWorld.Model.Types.UserRole;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Security.JWT.JwtUtils;
import com.wwanat.CryptoWorld.Security.Service.UserDetailsImpl;
import com.wwanat.CryptoWorld.Service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.wwanat.CryptoWorld.ServiceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * @author Wiktor
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtUtils.generateJwtToken(auth);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        List<String> userRoles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        logger.info("login request authenticated", AuthController.class);
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userRoles));
    }


    @PostMapping("/signup")
    public ResponseEntity registration(@RequestBody SignupRequest signupRequest) {

        String email = signupRequest.getEmail();
        String username = signupRequest.getUsername();
        try {
            if (userService.userExistByEmail(email) || userService.userExistByUsername(username)) {
                logger.error("User with given email/username already exists", AuthController.class);
                return ResponseEntity.badRequest().body(new MessageResponse("User with given username/email already exists"));
            } else {
                User user = new User(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getEmail());
                List<UserRole> registratingUserRoles = new ArrayList<>();
                List<String> requestUserRoles = signupRequest.getRoles();

                if (requestUserRoles != null) {
                    requestUserRoles.forEach(role -> {
                        if (role.contains("admin")) {
                            registratingUserRoles.add(UserRole.ROLE_ADMIN);
                        } else if (role.contains("user") || role.isEmpty()) {
                            registratingUserRoles.add(UserRole.ROLE_USER);
                        }
                    });
                } else {
                    registratingUserRoles.add(UserRole.ROLE_USER);
                }
                user.setRoles(registratingUserRoles);
                userService.createUser(user);
                logger.info("Sign up requested ended successfully", AuthController.class);
                return ResponseEntity.ok(new MessageResponse("Successfully registered"));
            }
        }catch(IllegalArgumentException illegalArgumentException){
            logger.error("IllegalArgumentException thrown in AuthController "+illegalArgumentException.getMessage(), AuthController.class);
            return ResponseEntity.badRequest().body(new MessageResponse("Email or username was null!"));
        }
        catch(Exception e){
            logger.error("Exception thrown in AuthController "+e.getMessage(), AuthController.class);
            return ResponseEntity.badRequest().body(new MessageResponse("Exception thrown at registration"));
        }
    }
}
