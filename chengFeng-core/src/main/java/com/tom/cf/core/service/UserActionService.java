package com.tom.cf.core.service;

import com.tom.cf.core.dao.repository.UserActionRepository;
import com.tom.cf.core.entity.FcAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserActionService {

    @Autowired
    private UserActionRepository userActionRepository;

    public List<FcAction> findActionList(){
        return userActionRepository.findAll();
    }
}
