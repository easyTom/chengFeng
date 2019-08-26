package com.tom.cf.mobile.api;

import com.tom.cf.core.entity.User;
import com.tom.cf.core.mobile.*;
import com.tom.cf.core.service.AuthService;
import com.tom.cf.core.service.UserService;
import com.tom.cf.core.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @method: 手机验证
 */
@RestController
@RequestMapping("/mobile/auth")
public class AuthApi extends BaseController {

    @Autowired
    public UserService userService;
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDTO<TokenDTO> login(@Valid LoginModel login,
        BindingResult bindingResult) {

            ResultDTO<TokenDTO> res = ResultDTO.newInstance();
            boolean valid = validateDTO(bindingResult);
            if (!valid) {
                return res.setResultCode(ResultCode.ILLEGAL_ARGUMENT);
            }

            User user = userService.getUserByName(login.getAccount());
            if(user == null || !user.getPassword().equals(ShiroUtils.sha256(login.getPassword(), user.getSalt()))){
                // 用户名密码无效
                res.setResultCode(ResultCode.FAIL);
                res.setResultMsg("用户名或密码无效");
                return res;
            }
            login.setPassword(user.getPassword());
            AuthEntity auth = authService.findAuth(login);
            TokenDTO token = new TokenDTO();
            if (auth == null) {
                auth = AuthEntity.buildFrom(login, token, user);
                // 1,删除原有auth，2,添加新授权
                authService.saveOrUpdate(auth);
            } else {
                // 同一账户，同一设备，返回同一token
                token.setToken(auth.getToken());
            }
            res.setData(token);
            return res;
    }

}
