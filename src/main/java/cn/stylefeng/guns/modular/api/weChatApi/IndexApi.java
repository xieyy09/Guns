package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/weChatApi")
@Slf4j
public class IndexApi extends BaseController {

    private String PREFIX = "/weChat/";
    @RequestMapping("/login")
    public String login() {
        return PREFIX + "login.html";
    }

    @RequestMapping("/addWork")
    public String addWork(@RequestParam("activityId") String activityId,
                          @RequestParam("activityName") String activityName,
                          Model model) {
        Object attribute = super.getSession().getAttribute(AuthUtil.OPENID);
        if(attribute == null){
            return PREFIX + "login.html";
        }
        model.addAttribute("activityId",activityId);
        return PREFIX + "addWork.html";
    }
}
