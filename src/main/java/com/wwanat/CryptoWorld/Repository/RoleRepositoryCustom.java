/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.Types.UserType;
import com.wwanat.CryptoWorld.Model.Role;

/**
 * @author Wiktor
 */
public interface RoleRepositoryCustom {

    Role findByRoleName(UserType name);

}
