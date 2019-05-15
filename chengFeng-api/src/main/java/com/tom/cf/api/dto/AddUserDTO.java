package com.tom.cf.api.dto;

import com.tom.cf.core.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @method:添加用户类
 */
@Data
public class AddUserDTO implements Serializable {

    private String userName;
    private String password;
    private String memo;
    private String mobile;

    public User convert(){
        User u = new User();
        BeanUtils.copyProperties(this, u);
        return u;
    }
}
