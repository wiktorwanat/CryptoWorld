/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Model.Types.UserType;
import com.wwanat.CryptoWorld.Model.Role;
import com.wwanat.CryptoWorld.Repository.RoleRepository;
import com.wwanat.CryptoWorld.Service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wiktor
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(UserType name) throws Exception {
        if (name != null) {
            return roleRepository.findByRoleName(name);
        } else {
            logger.info("Null pointer exception thrown in findByRoleName method", RoleServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

}
