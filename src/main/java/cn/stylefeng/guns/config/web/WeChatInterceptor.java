package cn.stylefeng.guns.config.web;

import cn.stylefeng.guns.config.util.AuthUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
@Slf4j
public class WeChatInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(log.isDebugEnabled()){
            log.debug("weChat Interceptor begin URL -->{}",request.getRequestURL());
        }
        Cookie[] cookies = request.getCookies();
        String cookieJsonStr=null;
        //request.getSession().setAttribute(AuthUtil.OPENID,"oW5mljpe9J8HsnTXUUtIirIk9Fh0");
        Object attribute1 = request.getSession().getAttribute(AuthUtil.OPENID);
        //判断cookie中是否存在openid 若存在则直接跳过，不存在则授权获取一次
        if(cookies!=null&&attribute1==null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(AuthUtil.OPENID+AuthUtil.NICKNAME)){
                    cookieJsonStr = cookie.getValue();
                }
            }
            if(cookieJsonStr!=null) {
                cookieJsonStr = URLDecoder.decode(cookieJsonStr, "utf-8");
                JSONObject jsonObject = JSON.parseObject(cookieJsonStr);
                String openId = jsonObject.getString(AuthUtil.OPENID);
                String nickname = jsonObject.getString(AuthUtil.NICKNAME);

                request.getSession().setAttribute(AuthUtil.OPENID, openId);
                request.getSession().setAttribute(AuthUtil.NICKNAME, nickname);
            }
        }else{
            //response.sendRedirect("");
//            return false;
        }
        Object attribute = request.getSession().getAttribute(AuthUtil.OPENID);
        if(request.getRequestURL().toString().contains("/authc/")) {
            if (attribute == null) {
                response.setStatus(403);
                return false;
//            request.getSession().setAttribute(AuthUtil.OPENID,"oronv1EbxSldBgO9kar4B7XGBC5w");
//            request.getSession().setAttribute(AuthUtil.NICKNAME, "刘修吉");
            }
        }
        if(log.isDebugEnabled()){
            log.debug("weChat Interceptor openId -->{}",attribute);
        }
        return true;
    }
}
