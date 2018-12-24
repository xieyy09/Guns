package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.modular.system.model.AccountExt;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IAccountExtService;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/weChatApi")
@Slf4j
public class LoginApi  extends BaseController {
    @Autowired
    private GunsProperties gunsProperties;
    @Autowired
    private IAccountExtService accountExtService;
    @Autowired
    private IUserService userService;
    /**
     * 上传图片
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

    @RequestMapping(method = RequestMethod.GET, path = "/callBack")
    @ResponseBody
    public Object callBack() {
        try {
            String code = super.getHttpServletRequest().getParameter("code");
            if(log.isDebugEnabled()){
                log.debug("code:----->{}",code);
            }
            //获取code后，请求以下链接获取access_token
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID
                    + "&secret=" + AuthUtil.APPSECRET
                    + "&code=" + code
                    + "&grant_type=authorization_code";

            //通过网络请求方法来请求上面这个接口
            JSONObject jsonObject = AuthUtil.doGetJson(url);
            if(log.isDebugEnabled()){
                log.debug("jsonObject:----->{}",jsonObject);
            }
            //从返回的JSON数据中取出access_token和openid，拉取用户信息时用
            String token =  jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            // 第三步：刷新access_token（如果需要）

            // 第四步：拉取用户信息(需scope为 snsapi_userinfo)
            String infoUrl ="https://api.weixin.qq.com/sns/userinfo?access_token=" + token
                    + "&openid=" + openid
                    + "&lang=zh_CN";
            //通过网络请求方法来请求上面这个接口
            JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
            if(log.isDebugEnabled()){
                log.debug("userInfo:----->{}",userInfo);
            }
            Wrapper<AccountExt> wrapper=new EntityWrapper<>();
            wrapper.eq("webchat_open_id",openid);
            AccountExt accountExt = accountExtService.selectOne(wrapper);
            if(accountExt==null){
                //未绑定
                setUserInfo(openid, userInfo,null);
                if(log.isDebugEnabled()){
                    log.debug("build ok:----->{}",openid);
                }
            }else{
                //已绑定
                setUserInfo(openid, userInfo,accountExt.getUid());
                if(log.isDebugEnabled()){
                    log.debug("update ok:----->{}",openid);
                }
            }
            super.getSession().setAttribute(AuthUtil.OPENID,openid);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "授权失败！");
        }
    }

    private void setUserInfo(String openid, JSONObject userInfo,Integer id) {
        AccountExt accountExt;
        accountExt=new AccountExt();
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
            accountExt.setUid(user.getId());
        }
        userService.insertOrUpdate(user);
        accountExtService.insertOrUpdate(accountExt);
    }
}
