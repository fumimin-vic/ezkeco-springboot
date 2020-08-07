package com.rio.ezkeco.aop;/**
 * @author vic fu
 **/

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *@author vic fu
 */
@Component
public class TokenInterceptor  extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.setHeader("aaa","aaa");
        return super.preHandle(request, response, handler);
    }
}
