package com.tom.cf.core.service;

import com.tom.cf.core.dao.repository.UserRightRepository;
import com.tom.cf.core.entity.UseRright;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserRightService {

    @Autowired
    private UserRightRepository nmUserRightRepository;

    public Set<String> findActionAliasByUserId(String userId){
        return nmUserRightRepository.findActionAliasByUserId(userId);
    }

    public void insertOrUpdate(UseRright useRright){
        nmUserRightRepository.save(useRright);
    }

}
