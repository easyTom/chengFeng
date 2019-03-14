package com.tom.cf.config;/*
package com.bsc.nmis.config;

import com.alibaba.fastjson.JSON;
import com.bsc.nmis.core.dao.entity.User;
import com.bsc.nmis.core.service.UserService;
import com.bsc.nmis.rest.api.dto.response.HttpCode;
import com.bsc.nmis.rest.api.dto.response.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ApiInterceptor extends HandlerInterceptorAdapter {

    private final static Logger LOG = LoggerFactory
            .getLogger(ApiInterceptor.class);

    private final static String AUTH = "auth";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if(!request.getRequestURI().startsWith("/api/")){
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 验证token
        String token = request.getHeader(AUTH);
        User user = userService.findUserByToken(token);
        boolean b = user != null;
        if (!b) {
            HttpCode code = HttpCode.UNAUTHORIZED;
            LOG.info(code.toString());
            String msg = JSON.toJSONString(new ResultDTO<String>(code))
                    .toString();
            writeResponse(response, msg);
        }else{
            request.getSession().setAttribute("userId",user.getUserId());
        }
        return b;
    }

    private void writeResponse(HttpServletResponse response, String msg) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(msg);

        } catch (IOException e) {
            LOG.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }

    }
}
*/
