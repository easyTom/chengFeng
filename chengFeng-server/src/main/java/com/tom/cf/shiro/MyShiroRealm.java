package com.tom.cf.shiro;

import com.tom.cf.core.entity.User;
import com.tom.cf.core.service.UserRightService;
import com.tom.cf.core.service.UserService;
import com.tom.cf.core.utils.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

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
        User u = (User)principalCollection.getPrimaryPrincipal();
        Set<String> permissions = nmUserRightService.findActionAliasByUserId(u.getUserId());
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    //用户认证
    //注意看return的是对象而之前是name 所以得改header.html的标签 也得改WebUtil的方法。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = nmUserService.findUserByUserName(name);
        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            /*SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
            return simpleAuthenticationInfo;*/
            //盐值加密
            return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
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

    /**
     * 盐值加密
     */
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

}