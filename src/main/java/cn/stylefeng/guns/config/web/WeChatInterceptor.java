package cn.stylefeng.guns.config.web;

import cn.stylefeng.guns.config.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class WeChatInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(log.isDebugEnabled()){
            log.debug("weChat Interceptor begin URL -->{}",request.getRequestURL());
        }
        Object attribute = request.getSession().getAttribute(AuthUtil.OPENID);
        if(attribute==null){
            response.setStatus(403);
            return false;
        }
        if(log.isDebugEnabled()){
            log.debug("weChat Interceptor openId -->{}",attribute);
        }
        return true;
    }
}
