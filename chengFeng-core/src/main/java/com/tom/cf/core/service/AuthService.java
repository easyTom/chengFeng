package com.tom.cf.core.service;

import com.tom.cf.core.dao.mapper.AuthMapper;
import com.tom.cf.core.dao.repository.AuthRepository;
import com.tom.cf.core.mobile.AuthEntity;
import com.tom.cf.core.mobile.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private AuthRepository authRepository;

   /* public AuthEntity findAuthByToken(String token) {
        return authMapper.findAuthByToken(token);
    }*/


    public AuthEntity findAuth(LoginModel login){
        return authMapper.findAuth(login);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(AuthEntity authEntity) {
        authEntity.setCreateTime(new Date());
        authRepository.save(authEntity);
    }
    @Transactional(rollbackFor = Exception.class)
    public void delete(AuthEntity auth) {
        authRepository.delete(auth);
    }
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(AuthEntity auth) {
        authRepository.delete(auth);
        auth.setCreateTime(new Date());
        authRepository.save(auth);
    }

}
