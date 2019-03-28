package com.tom.cf.api.dto;

import com.tom.cf.core.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class AuthorityDTO {

    private String userId;

    private Integer status;

    private String memo;

    private List<String> authority;

    public static User valueOf(AuthorityDTO authorityDTO){
        User user = new User();
        BeanUtils.copyProperties(authorityDTO,user);
        return user;
    }

}
