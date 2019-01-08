package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.model.AccountExt;
import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IAccountExtService;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.util.FileUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/weChatApi/authc/userInfo")
@Slf4j
public class UserApi extends BaseController {
    @Autowired
    private GunsProperties gunsProperties;
    @Autowired
    private IAccountExtService accountExtService;
    @Autowired
    private IUserService userService;

    /**
     * 获取用户详细信息，根据openId
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getUserInfo")
    @ResponseBody
    public Map<String,Object> getUserInfo() {
        Map<String,Object> dataMap=new HashMap<>();
        String openId = super.getSession().getAttribute(AuthUtil.OPENID).toString();
        Wrapper<AccountExt> warpper=new EntityWrapper<>();
        AccountExt accountExt = accountExtService.selectOne(warpper);
        Integer uid = accountExt.getId();
        User user = userService.selectById(uid);
        dataMap.put("name",user.getName());
        dataMap.put("sex",user.getSex());
        dataMap.put("img",accountExt.getWebchatPohtoUrl());
        dataMap.put("webchatName",accountExt.getWebchatName());
        dataMap.put("phone",user.getPhone());
        return dataMap;
    }

    /**
     * 获取用户详细信息，根据openId
     */
    @RequestMapping(method = RequestMethod.POST, path = "/updateUserInfo")
    @ResponseBody
    public Object updateUserInfo(@RequestParam("name") String name,
                                 @RequestParam("sex") Integer sex,
                                 @RequestParam("img") String img,
                                 @RequestParam("webchatName") String webchatName,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("loginName") String loginName,
                                 @RequestParam("loginPass") String loginPass) {
        try {
            Wrapper<User> warpperCount=new EntityWrapper<>();
            Integer uid = getUserId();
            warpperCount.eq("phone",phone).and().notIn("id", uid);
            int count = userService.selectCount(warpperCount);
            if(count>0){
                return new ErrorResponseData(500, "联系电话已经存在，请更换！");
            }
            warpperCount=new EntityWrapper<>();
            warpperCount.eq("account",loginName).and().notIn("id", uid);
            int nameCount  = userService.selectCount(warpperCount);
            if(nameCount>0){
                return new ErrorResponseData(500, "登陆账号已经存在，请更换！");
            }
            User user=new User();
            user.setId(uid);
            user.setName(name);
            user.setAccount(loginName);
            user.setPassword(loginPass);
            user.setSex(sex);
            user.setPhone(phone);
            userService.updateById(user);
            Wrapper<AccountExt> warpper=new EntityWrapper<>();
            warpper.eq("id",uid);
            AccountExt accountExt = accountExtService.selectOne(warpper);
            AccountExt accountExtNew=new AccountExt();
            BeanUtils.copyProperties(accountExt,accountExtNew);
            accountExtNew.setWebchatName(webchatName);
            accountExtNew.setWebchatPohtoUrl(img);
            if(!accountExt.equals(accountExtNew)){
                accountExtNew.setIsEdit(1);
            }
            accountExtService.updateById(accountExtNew);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "更新信息失败！");
        }
    }
    private Integer getUserId() {
        Object openId = super.getSession().getAttribute(AuthUtil.OPENID);
        Wrapper<AccountExt> warpper=new EntityWrapper<>();
        warpper.eq("webchat_open_id",openId.toString());
        AccountExt accountExt = accountExtService.selectOne(warpper);
        if(accountExt==null){
            throw new ServiceException(BizExceptionEnum.TOKEN_EXPIRED);
        }
        return accountExt.getId();
    }
}
