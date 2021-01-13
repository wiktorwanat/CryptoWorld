/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Wiktor
 */
@Document(collection = "roles")
public class Role {
    
    @Id
    private String id;
    
    private EnumRole roleName;

    public Role() {
    }

    public Role(EnumRole roleName) {
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EnumRole getRoleName() {
        return roleName;
    }

    public void setRoleName(EnumRole roleName) {
        this.roleName = roleName;
    }
    
    
}
