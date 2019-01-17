package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.AesException;
import cn.stylefeng.guns.core.util.DownloadImageUtil;
import cn.stylefeng.guns.core.util.WXPublicUtils;
import cn.stylefeng.guns.modular.system.model.AccountExt;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IAccountExtService;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.wechat.dispatcher.EventDispatcher;
import cn.stylefeng.guns.wechat.dispatcher.MsgDispatcher;
import cn.stylefeng.guns.wechat.util.MessageUtil;
import cn.stylefeng.guns.wechat.util.SignUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.FileUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/weChatApi")
@Slf4j
public class LoginApi  extends BaseController {
    @Autowired
    private GunsProperties gunsProperties;
    @Autowired
    private IAccountExtService accountExtService;
    @Autowired
    private IUserService userService;
    @Autowired
    private DownloadImageUtil downloadImageUtil;
    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public Object upload(@RequestParam("mediaId") String mediaId,@RequestParam("path") String path) {
        try {
            String filename=downloadImageUtil.getImageFromWechat(mediaId,path);
            return new SuccessResponseData(filename);
        } catch (Exception e) {
            return  new ServiceException(500,"上传失败");
        }
    }
    /**
     * 加载图片
     */
    @RequestMapping(method = RequestMethod.GET, path = "/loadImg")
    @ResponseBody
    public byte[] loadImg(@RequestParam("filename") String filename, @RequestParam("path") String path) {

        String fileSavePath = gunsProperties.getFileUploadPath();
        try {
            String pathname = fileSavePath + path+ File.separator+filename;
            return FileUtil.toByteArray(pathname);
        } catch (Exception e) {
            String pathname = fileSavePath + "noimage.png";
            return FileUtil.toByteArray(pathname);
        }

    }
    @RequestMapping(method = RequestMethod.GET, path = "/verify_wx_token")
    @ResponseBody
    public void verify_wx_token() throws AesException {
        HttpServletRequest request = super.getHttpServletRequest();
        HttpServletResponse response = super.getHttpServletResponse();
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;
        try {
            out=response.getWriter();
            if (SignUtil.checkSignature(msgSignature, msgTimestamp, msgNonce)) {
                out.print(echostr);
            }
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }finally {
            out.close();
            out=null;
        }
    }
    @RequestMapping(method = RequestMethod.POST, path = "/userNameLogin")
    @ResponseBody
    public Object userNameLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
        Wrapper<User> wrapper=new EntityWrapper<>();
        Map<String,Object> data=new HashMap<>();
        try {
            wrapper.eq("account",username).and().eq("password",password);
            User user = userService.selectOne(wrapper);
            if(user==null){
                return  new ErrorResponseData("账户或密码错误");
            }
            AccountExt accountExt = accountExtService.selectById(user.getId());
            super.getSession().setAttribute(AuthUtil.OPENID, accountExt.getWebchatOpenId());
            super.getSession().setAttribute(AuthUtil.NICKNAME, accountExt.getWebchatName());
            return  new SuccessResponseData(accountExt.getWebchatName());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return  new ServiceException(500,"登陆失败");
        }

    }

    /**
     * 接收微信端消息处理并做分发
     */
    @RequestMapping(method = RequestMethod.POST, path = "/verify_wx_token")
    public void postCallBack(HttpServletRequest request,HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try{
            Map<String, String> map=MessageUtil.parseXml(request);
            log.debug(JSON.toJSON(map).toString());
            String msgtype=map.get("MsgType");
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
                String msgrsp= EventDispatcher.processEvent(map); //进入事件处理
                PrintWriter out = response.getWriter();
                out.print(msgrsp);
                out.close();
            }else{
                String msgrsp= MsgDispatcher.processMessage(map); //进入消息处理
                PrintWriter out = response.getWriter();
                out.print(msgrsp);
                out.close();
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
    }
    @RequestMapping(method = RequestMethod.GET, path = "/callBack")
    @ResponseBody
    public void callBack() {
        try {
            String code = super.getHttpServletRequest().getParameter("code");
            HttpServletResponse response = super.getHttpServletResponse();
            if(code==null || code.equals("")){
                String wx_redirect_uri= URLEncoder.encode(AuthUtil.REDIRECT_URI);
                String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+AuthUtil.APPID
                        + "&redirect_uri="+wx_redirect_uri
                        + "&response_type=code"
                        + "&scope=snsapi_userinfo"
                        + "&state=STATE#wechat_redirect";
                response.sendRedirect(redirectUrl);
            }else {
                Cookie[] cookies = super.getHttpServletRequest().getCookies();
                String cookieJsonStr =null;
                //判断cookie中是否存在openid 若存在则直接跳过，不存在则获取一次
                if(cookies!=null){
                    for(Cookie cookie : cookies){
                        if(cookie.getName().equals(AuthUtil.OPENID+AuthUtil.NICKNAME)){
                            cookieJsonStr = cookie.getValue();
                        }
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("code:----->{}", code);
                }
                if(cookieJsonStr!=null){
                    cookieJsonStr =  URLDecoder.decode(cookieJsonStr,"utf-8");
                    JSONObject jsonObject = JSON.parseObject(cookieJsonStr);
                    String openId = jsonObject.getString(AuthUtil.OPENID);
                    String nickname = jsonObject.getString(AuthUtil.NICKNAME);

                    super.getSession().setAttribute(AuthUtil.OPENID,openId);
                    super.getSession().setAttribute(AuthUtil.NICKNAME,nickname);
                    if (log.isDebugEnabled()) {
                        log.debug("openId:----->{}", openId);
                    }
                    response.sendRedirect(AuthUtil.SERVER);
                }
                //获取code后，请求以下链接获取access_token
                String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID
                        + "&secret=" + AuthUtil.APPSECRET
                        + "&code=" + code
                        + "&grant_type=authorization_code";

                //通过网络请求方法来请求上面这个接口
                JSONObject jsonObject = AuthUtil.doGetJson(url);
                if (log.isDebugEnabled()) {
                    log.debug("jsonObject:----->{}", jsonObject);
                }
                //从返回的JSON数据中取出access_token和openid，拉取用户信息时用
                String token = jsonObject.getString("access_token");
                String openid = jsonObject.getString("openid");
                // 第三步：刷新access_token（如果需要）
//                AuthUtil.ACCESS_TOKEN = token;
                // 第四步：拉取用户信息(需scope为 snsapi_userinfo)
                String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token
                        + "&openid=" + openid
                        + "&lang=zh_CN";
                //通过网络请求方法来请求上面这个接口
                JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
                if (log.isDebugEnabled()) {
                    log.debug("userInfo:----->{}", userInfo);
                }
                Wrapper<AccountExt> wrapper = new EntityWrapper<>();
                wrapper.eq("webchat_open_id", openid);
                AccountExt accountExt = accountExtService.selectOne(wrapper);
                String nickname="";
                if (accountExt == null) {
                    //未绑定
                    nickname = setUserInfo(openid, userInfo, null);
                    if (log.isDebugEnabled()) {
                        log.debug("build ok:----->{}", openid);
                    }
                } else {
                    //已绑定
                    nickname = setUserInfo(openid, userInfo, accountExt.getId());
                    if (log.isDebugEnabled()) {
                        log.debug("update ok:----->{}", openid);
                    }
                }
                Map<String,Object> cookieMap = new HashMap<>();
                cookieMap.put(AuthUtil.OPENID,openid);
                cookieMap.put(AuthUtil.NICKNAME,nickname);
                String encode = URLEncoder.encode(JSON.toJSONString(cookieMap), "utf-8");
                //获取微信用户openid存储在cookie中的信息
                Cookie userCookie=new Cookie(AuthUtil.OPENID+AuthUtil.NICKNAME,encode);
                userCookie.setMaxAge(Integer.MAX_VALUE);
                userCookie.setPath("/");
                response.addCookie(userCookie);
                super.getSession().setAttribute(AuthUtil.OPENID, openid);
                super.getSession().setAttribute(AuthUtil.NICKNAME, nickname);
                response.sendRedirect(AuthUtil.SERVER);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
//            return new ErrorResponseData(500, "授权失败！");
        }
    }
    @RequestMapping(method = RequestMethod.GET, path = "/getNickname")
    @ResponseBody
    public Object getNickname() {
        Map<String,Object> data=new HashMap<>();
        Object attribute = super.getSession().getAttribute(AuthUtil.NICKNAME);
        if(attribute==null){
            return  new ErrorResponseData("未登陆");
        }
        return new SuccessResponseData(attribute.toString());
    }
    private String setUserInfo(String openid, JSONObject userInfo,Integer id) {
        AccountExt accountExt=new AccountExt();
        Integer sex = userInfo.getInteger("sex");
        String nickname = userInfo.getString("nickname");
        String headimgurl = userInfo.getString("headimgurl");
        User user=new User();
        user.setSex(sex);
        user.setName(nickname);
        user.setAccount("weChat");

        accountExt.setWebchatOpenId(openid);
        accountExt.setWebchatName(nickname);
        accountExt.setWebchatPohtoUrl(headimgurl);
        if(id!=null){
            user.setId(id);
            user.setStatus(1);
            user.setCreatetime(new Date());
            accountExt.setId(user.getId());
            userService.updateById(user);
            accountExtService.updateById(accountExt);
        }else{
            userService.insert(user);
            accountExt.setId(user.getId());
            accountExtService.insert(accountExt);
        }
        return nickname;
    }

}
