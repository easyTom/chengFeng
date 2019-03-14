package com.tom.cf.ui;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tom/user")
public class UserController {

    private static final String MANAGE="backend/pages/registration/user_manage";

    private static final String MANAGE_DETAIL="backend/pages/registration/user_manage_detail";

    private static final String INDEX = "backend/pages/index";

    @GetMapping("/index")
    public String index(){
        return INDEX;
    }
    @GetMapping("/manage")
    public String userManage(){
        return MANAGE;
    }

    @GetMapping("/manage/detail/{id}")
    public String detailId(@PathVariable String id, Model model) {
        model.addAttribute("userId",id);
        return MANAGE_DETAIL;
    }


    //测试登录
    @RequiresPermissions("test")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test(String userName,String password){
        return "ok";
    }


}
