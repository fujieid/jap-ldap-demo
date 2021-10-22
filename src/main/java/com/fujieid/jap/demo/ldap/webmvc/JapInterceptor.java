package com.fujieid.jap.demo.ldap.webmvc;

import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.http.adapter.jakarta.JakartaRequestAdapter;
import com.fujieid.jap.http.adapter.jakarta.JakartaResponseAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模拟登录拦截器
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-02-26 11:06
 * @since 1.0.0
 */
@Slf4j
@Component
public class JapInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURI());
        JapResponse japResponse = JapAuthentication.checkUser(new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
        if (japResponse.isSuccess()) {
            log.info("已登录");
            return true;
        }
        log.info("未登录");
        response.sendRedirect("/?error=" + URLUtil.encode("未登录"));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
