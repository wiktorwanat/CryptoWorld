/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Wiktor
 */
@Document(collection="users")
public class User {
    
    @Id
    private String id;
    
    private String username;
    
    private String password;
    
    private String email;
    
    @DBRef
    private Set<Role> roles = new HashSet<>();
    
    @DBRef
    private List<Cryptocurrency> userCryptocurrency=new ArrayList<Cryptocurrency>();


    public User() {
    }

    public User(String username, String password, String email) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Cryptocurrency> getUserCryptocurrency() {
        return userCryptocurrency;
    }

    public void setUserCryptocurrency(List<Cryptocurrency> userCryptocurrency) {
        this.userCryptocurrency = userCryptocurrency;
    }

    public void addFavouriteCryptocurrency(Cryptocurrency cryptocurrency){
        if(cryptocurrency!=null){
            this.userCryptocurrency.add(cryptocurrency);
        }
    }
    
    public void removeFavouriteCryptocurrency(Cryptocurrency cryptocurrency){
        if(cryptocurrency!=null){
            this.userCryptocurrency.remove(cryptocurrency);
        }
    }

    
}
