package com.tom.cf.api;

import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.service.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tom/api/action")
public class UserActionApi {

    @Autowired
    private UserActionService userActionService;

    @PostMapping("/actionList")
    public ResultDTO findActionList(){
        ResultDTO res = ResultDTO.newInstance();
        res.setData(userActionService.findActionList());
        return res;
    }

}
