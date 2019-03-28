package com.tom.cf.api;

import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.service.UserRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/tom/api/userRight")
public class UserRightApi {

    @Autowired
    private UserRightService userRightService;

    @PostMapping("/getByUserId")
    public ResultDTO detail(String userId){
        ResultDTO res = ResultDTO.newInstance();
        Set<String> list=  userRightService.findActionAliasByUserId(userId);
        res.setData(list);
        return res;
    }
}
