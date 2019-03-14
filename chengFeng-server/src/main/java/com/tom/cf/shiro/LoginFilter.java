package com.tom.cf.shiro;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UserFilter {

    private Map<String,String> loginUrlMap = new HashMap();
    private String casService;
    private String casServerUrlPrefix;

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        final HttpServletRequest req = (HttpServletRequest) request;
        String host = req.getHeader("host");
        StringBuilder loginUrl = new StringBuilder();
        loginUrlMap.forEach((k,v) -> {
            if(k.contains(host)){
                loginUrl.append(v);
            }
        });

        WebUtils.issueRedirect(request, response, loginUrl.toString());
    }

    public Map<String, String> getLoginUrlMap() {
        return loginUrlMap;
    }

    public void setLoginUrlMap(Map<String, String> loginUrlMap) {
        this.loginUrlMap = loginUrlMap;
    }

    public String getCasService() {
        return casService;
    }

    public void setCasService(String casService) {
        this.casService = casService;
    }

    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

}
