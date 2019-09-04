package com.tom.cf.ui.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/frontend")
public class TomFrontendController {
	
	// 跳转到登录页面
    @RequestMapping("/login")
    public String login(){
        return "frontend/login";
    }
	
}
