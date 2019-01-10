package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.modular.activity.service.IActivityDetailsService;
import cn.stylefeng.guns.modular.headline.service.IHeadlineInfoService;
import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.guns.modular.system.model.HeadlineInfo;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 活动API
 */
@RestController
@RequestMapping("/weChatApi/headline")
@Slf4j
public class HeadlineApi extends BaseController {
    @Autowired
    private IHeadlineInfoService headlineInfoService;

    @GetMapping("/list")
    public Object findHeadlineList(@RequestParam(required=true,defaultValue="1") Integer page){
        try {
            Page<HeadlineInfo> pages =  new Page<>(page,12);
            Wrapper<HeadlineInfo> wrapper=new EntityWrapper<>();
            wrapper.where("status=1");
            Page<HeadlineInfo> headlineInfoPage = headlineInfoService.selectPage(pages,wrapper);
            return  new SuccessResponseData(headlineInfoPage);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询头条列表失败！");
        }
    }
}
