package com.tom.cf.core.service;

import com.tom.cf.core.dao.repository.UserRepository;
import com.tom.cf.core.entity.User;
import com.tom.cf.core.utils.ShiroUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String findIdByUserName(String userName){
        return userRepository.findIdByUserName(userName);
    }

    public void insertUser(User user){
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable){

        return userRepository.findAll(pageable);
    }
    
    public User findUserByUserId(String userId){
    	return userRepository.findById(userId).get();
    }

    public List<User> findUserList(Specification specification){
       return userRepository.findAll(specification);
    }

    public void insertOrupdateUser(User user){
        userRepository.updateUserByUserId(user);
    }

    public User findUserByUserName(String userName){
        return userRepository.findByUserNameAndStatus(userName,1);
    }

    public User findUserByToken(String token){
        return userRepository.findByToken(token);
    }
}
