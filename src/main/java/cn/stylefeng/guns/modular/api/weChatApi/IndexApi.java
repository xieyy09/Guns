package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.roses.core.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weChatApi")
@Slf4j
public class IndexApi extends BaseController {

    private String PREFIX = "/weChat/";
    @RequestMapping("/login")
    public String index() {
        return PREFIX + "login.html";
    }
}
