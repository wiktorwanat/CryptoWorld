/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Model;

import com.wwanat.CryptoWorld.Model.Types.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Wiktor
 */
@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    private UserType roleName;

    public Role() {
    }

    public Role(UserType roleName) {
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserType getRoleName() {
        return roleName;
    }

    public void setRoleName(UserType roleName) {
        this.roleName = roleName;
    }


}
