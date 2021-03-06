package com.tom.cf.core.service;

import com.tom.cf.core.dao.repository.UserRightRepository;
import com.tom.cf.core.entity.FcUserRright;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserRightService {

    @Autowired
    private UserRightRepository userRightRepository;

    public Set<String> findActionAliasByUserId(String userId){
        return userRightRepository.findActionAliasByUserId(userId);
    }

    public void insertOrUpdate(FcUserRright fcUserRright){
        userRightRepository.save(fcUserRright);
    }

    @Transactional
    public void DeleteByUserId(String userId) {
        userRightRepository.deleteById(userId);
    }
}
