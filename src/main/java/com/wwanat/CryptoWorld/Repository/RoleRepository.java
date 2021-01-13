/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.EnumRole;
import com.wwanat.CryptoWorld.Model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wiktor
 */
@Repository
public interface RoleRepository extends MongoRepository<Role,String>,RoleRepositoryCustom{
    
    Role findByRoleName(EnumRole name);
}
