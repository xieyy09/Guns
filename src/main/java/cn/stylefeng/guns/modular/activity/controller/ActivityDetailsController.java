package cn.stylefeng.guns.modular.activity.controller;

import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.guns.modular.activity.service.IActivityDetailsService;

import java.util.Date;

/**
 * 活动管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-08 21:16:47
 */
@Controller
@RequestMapping("/activityDetails")
public class ActivityDetailsController extends BaseController {

    private String PREFIX = "/activity/activityDetails/";

    @Autowired
    private IActivityDetailsService activityDetailsService;

    /**
     * 跳转到活动管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "activityDetails.html";
    }

    /**
     * 跳转到添加活动管理
     */
    @RequestMapping("/activityDetails_add")
    public String activityDetailsAdd() {
        return PREFIX + "activityDetails_add.html";
    }

    /**
     * 跳转到修改活动管理
     */
    @RequestMapping("/activityDetails_update/{activityDetailsId}")
    public String activityDetailsUpdate(@PathVariable String activityDetailsId, Model model) {
        ActivityDetails activityDetails = activityDetailsService.selectById(activityDetailsId);
        model.addAttribute("item",activityDetails);
        LogObjectHolder.me().set(activityDetails);
        return PREFIX + "activityDetails_edit.html";
    }

    /**
     * 获取活动管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return activityDetailsService.selectList(null);
    }

    /**
     * 新增活动管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ActivityDetails activityDetails) {
        activityDetails.setId(UUIDUtils.getBase64UUID());
        activityDetails.setCreateTime(new Date());
        activityDetails.setUid(1L);
        activityDetailsService.insert(activityDetails);
        return SUCCESS_TIP;
    }

    /**
     * 删除活动管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String activityDetailsId) {
        activityDetailsService.deleteById(activityDetailsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改活动管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ActivityDetails activityDetails) {
        activityDetailsService.updateById(activityDetails);
        return SUCCESS_TIP;
    }

    /**
     * 活动管理详情
     */
    @RequestMapping(value = "/detail/{activityDetailsId}")
    @ResponseBody
    public Object detail(@PathVariable("activityDetailsId") String activityDetailsId) {
        return activityDetailsService.selectById(activityDetailsId);
    }
}
