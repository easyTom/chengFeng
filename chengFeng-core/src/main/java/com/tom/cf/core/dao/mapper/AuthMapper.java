package com.tom.cf.core.dao.mapper;

import com.tom.cf.core.entity.User;
import com.tom.cf.core.mobile.AuthEntity;
import com.tom.cf.core.mobile.LoginModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @method: 验证相关
 */
@Mapper
public interface AuthMapper {
    //AuthEntity findAuthByToken(String token);

    AuthEntity findAuth(LoginModel login);

    User findByName(String account);
}
