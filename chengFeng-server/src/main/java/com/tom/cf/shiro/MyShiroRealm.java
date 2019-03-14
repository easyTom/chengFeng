package com.tom.cf.shiro;

import com.tom.cf.core.entity.User;
import com.tom.cf.core.service.UserRightService;
import com.tom.cf.core.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

    //用于用户查询

    private UserService nmUserService;

    private UserRightService nmUserRightService;

    //角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = (String)principalCollection.getPrimaryPrincipal();
        String userId = nmUserService.findIdByUserName(username);
        Set<String> permissions = nmUserRightService.findActionAliasByUserId(userId);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = nmUserService.findUserByUserName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
    }

    public UserRightService getNmUserRightService() {
        return nmUserRightService;
    }

    public void setNmUserRightService(UserRightService nmUserRightService) {
        this.nmUserRightService = nmUserRightService;
    }

    public UserService getNmUserService() {
        return nmUserService;
    }

    public void setNmUserService(UserService nmUserService) {
        this.nmUserService = nmUserService;
    }


}