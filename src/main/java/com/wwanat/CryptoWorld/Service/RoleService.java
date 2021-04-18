/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Service;

import com.wwanat.CryptoWorld.Model.EnumRole;
import com.wwanat.CryptoWorld.Model.Role;

/**
 *
 * @author Wiktor
 */
public interface RoleService {

     Role findByRoleName(EnumRole name) throws Exception;

}
