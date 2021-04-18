/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wiktor
**/
@Repository
public interface UserRepository extends MongoRepository<User,String>,UserRepositoryCustom{
    
    User findByUsername(String username);
    
    boolean userExistByUsername(String username);
    
    boolean userExistByEmail(String email);

    void deleteUserDependencies(String id);
}
