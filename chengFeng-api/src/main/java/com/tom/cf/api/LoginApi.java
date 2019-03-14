package com.tom.cf.api;

import com.tom.cf.core.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;


@Controller
public class LoginApi {


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username, String password, Model model) throws IOException {
        //添加用户认证信息
        String md5Password = MD5Utils.md5(password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                username,
                md5Password);
        System.out.println(md5Password);
		try {
			//进行验证，这里可以捕获异常，然后返回对应信息
			subject.login(usernamePasswordToken);
			return "redirect:/tom/user/index";
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		model.addAttribute("message","error");
		return "forward:/ui/frontend/login";
    }

    @RequestMapping("/logout")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/ui/frontend/login";
    }

}
