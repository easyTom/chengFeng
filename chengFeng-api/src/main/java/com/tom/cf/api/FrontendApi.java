package com.tom.cf.api;

import com.tom.cf.api.dto.HttpCode;
import com.tom.cf.api.dto.ResultDTO;
import com.tom.cf.core.entity.User;
import com.tom.cf.core.service.UserService;
import com.tom.cf.core.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/frontend")
public class FrontendApi {
	@Autowired
	UserService userService;
	
	// 注册用户名
    @PostMapping("/register")
    public ResultDTO<?> register(User user){
		ResultDTO resultDTO = ResultDTO.newInstance();
		resultDTO.setResultCode(HttpCode.BAD_REQUEST);
    	Map<String,Object> map = new HashMap<>();
    	map.put("result",false);
    	if(user != null){
			String password = user.getPassword();
			if(!StringUtils.isEmpty(password)){
				user.setPassword(MD5Utils.md5(password));
				user.setToken(MD5Utils.md5(user.getUserName() + password));
			}
			user.setStatus(0);
			user.setCreatedAt(new Date());
			userService.insertUser(user);
			resultDTO.setResultCode(HttpCode.OK);
			map.put("result",true);
    	}
        return resultDTO.setData(map);
    }
    
    // 查看用户名是否可用
    @GetMapping("/checkUsername")
    public void checkUsername(User nmUser, HttpServletResponse response) throws IOException {
		if(nmUser != null){
    		String username = nmUser.getUserName();
			if(!StringUtils.isEmpty(username)){
				String id = userService.findIdByUserName(username);
				if(StringUtils.isEmpty(id)){
					response.getWriter().print(true);
					return;
				}
			}
		}
		response.getWriter().print(false);
    }

}
