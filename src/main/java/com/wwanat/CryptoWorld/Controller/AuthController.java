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
import com.wwanat.CryptoWorld.Model.EnumRole;
import com.wwanat.CryptoWorld.Model.Role;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Security.JWT.JwtUtils;
import com.wwanat.CryptoWorld.Security.Service.UserDetailsImpl;
import com.wwanat.CryptoWorld.Service.RoleService;
import com.wwanat.CryptoWorld.Service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

/**
 *
 * @author Wiktor
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    final private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    
    @PostMapping("/signin")
    public ResponseEntity authenticate( @RequestBody LoginRequest loginRequest){
        Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        String jwt=jwtUtils.generateJwtToken(auth);
        
        UserDetailsImpl userDetails=(UserDetailsImpl) auth.getPrincipal();
        List<String> userRoles=userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
        logger.info("login request authenticated", AuthController.class);
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),userRoles));
    }
    
    
    @PostMapping("/signup")
    public ResponseEntity registration(@RequestBody SignupRequest signupRequest){
        
        String email=signupRequest.getEmail();
        String username=signupRequest.getUsername();
        if(email!=null && userService.userExistByEmail(email)){
            logger.error("User with given email already exists", AuthController.class);
            return ResponseEntity.badRequest().body(new MessageResponse("User with given email already exists"));
        }else if(username!=null && userService.userExistByUsername(username)){
            logger.error("User with given username already exists", AuthController.class);
            return ResponseEntity.badRequest().body(new MessageResponse("User with given username already exists"));
        }else{
            User user=new User(signupRequest.getUsername(),passwordEncoder.encode(signupRequest.getPassword()),signupRequest.getEmail());
            
            Set<Role> userRoles=new HashSet();
            Set<String> requestRole=signupRequest.getRoles();
            
            if(requestRole!=null){
                requestRole.forEach(role ->{
                    if(role.contains("admin")){
                        Role admin=roleService.findByRoleName(EnumRole.ROLE_ADMIN);
                        if(admin==null){
                            throw new RuntimeException("Error: Role is not found.");
                        }else{
                           userRoles.add(admin);
                        }
                    }else if(role.contains("user") || role.isEmpty()){
                        Role userRole=roleService.findByRoleName(EnumRole.ROLE_USER);
                        if(userRole==null){
                            throw new RuntimeException("Error: Role is not found.");
                        }else{
                           userRoles.add(userRole);
                        }
                    }
                });
            }else{
                Role userRole=roleService.findByRoleName(EnumRole.ROLE_USER);
                        if(userRole==null){
                            throw new RuntimeException("Error: Role is not found.");
                        }else{
                           userRoles.add(userRole);
                        }
            }
            user.setRoles(userRoles);
            userService.createUser(user);
            logger.info("signupRequest ended succesfully", AuthController.class);
            return ResponseEntity.ok(new MessageResponse("Succesfully registered"));
        }
    }
}
