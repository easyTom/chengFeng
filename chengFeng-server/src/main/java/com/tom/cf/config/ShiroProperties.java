package com.tom.cf.config;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Map;

@ConfigurationProperties(
        ignoreUnknownFields = false,
        prefix = "core.security.shiro"
)
public class ShiroProperties {
    @NotBlank
    private String casServerUrlPrefix;
    @NotBlank
    private String casService;
    private String loginUrl;
    private String successUrl;
    private String casValidatorUrl;
    //
    private String casServerUrlPrefix2;
    private String casService2;
    private String loginUrl2;
    private String successUrl2;
    private Map<String,String> filterChainDefinitionMap;

    @PostConstruct
    public void init(){
        if(StringUtils.isEmpty(this.loginUrl)){
            this.loginUrl = this.casServerUrlPrefix + "/login?service=" + this.casService;
        }
        if(StringUtils.isEmpty(this.loginUrl2)){
            this.loginUrl2 = this.casServerUrlPrefix2 + "/login?service=" + this.casService2;
        }
    }

    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public String getCasService() {
        return casService;
    }

    public void setCasService(String casService) {
        this.casService = casService;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public Map<String, String> getFilterChainDefinitionMap() {
        return filterChainDefinitionMap;
    }

    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        this.filterChainDefinitionMap = filterChainDefinitionMap;
    }

    public String getCasValidatorUrl() {
        return casValidatorUrl;
    }

    public void setCasValidatorUrl(String casValidatorUrl) {
        this.casValidatorUrl = casValidatorUrl;
    }

    public String getCasServerUrlPrefix2() {
        return casServerUrlPrefix2;
    }

    public void setCasServerUrlPrefix2(String casServerUrlPrefix2) {
        this.casServerUrlPrefix2 = casServerUrlPrefix2;
    }

    public String getCasService2() {
        return casService2;
    }

    public void setCasService2(String casService2) {
        this.casService2 = casService2;
    }

    public String getLoginUrl2() {
        return loginUrl2;
    }

    public void setLoginUrl2(String loginUrl2) {
        this.loginUrl2 = loginUrl2;
    }

    public String getSuccessUrl2() {
        return successUrl2;
    }

    public void setSuccessUrl2(String successUrl2) {
        this.successUrl2 = successUrl2;
    }
}
