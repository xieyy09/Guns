package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.modular.activity.service.IActivityDetailsService;
import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
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
import java.util.List;

/**
 * 活动API
 */
@RestController
@RequestMapping("/weChatApi/activity")
@Slf4j
public class ActivityApi extends BaseController {
    @Autowired
    private IActivityDetailsService activityDetailsService;

    @GetMapping("/list")
    public Object findChampionList(@RequestParam(required=true,defaultValue="1") Integer page){
        try {
            Page<ActivityDetails> pages =  new Page<>(page,12);
            Wrapper<ActivityDetails> wrapper=new EntityWrapper<>();
            wrapper.orderDesc(Arrays.asList("state","begin_time"));
            Page<ActivityDetails> activityDetailsList = activityDetailsService.selectPage(pages,wrapper);
            SuccessResponseData responseData = new SuccessResponseData();
            responseData.setData(activityDetailsList);
            responseData.setCode(ResponseData.DEFAULT_SUCCESS_CODE);
            responseData.setMessage(ResponseData.DEFAULT_SUCCESS_MESSAGE);
            return responseData;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询活动列表失败！");
        }
    }

    @GetMapping("/info")
    public Object findChampionInfo(@RequestParam("id") String id){
        try {
            ActivityDetails activityDetails = activityDetailsService.selectById(id);
            return activityDetails;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询活动详情失败！");
        }
    }
}
