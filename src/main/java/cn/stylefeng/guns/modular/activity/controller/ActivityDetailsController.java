package cn.stylefeng.guns.modular.activity.controller;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.FileUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.guns.modular.activity.service.IActivityDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private GunsProperties gunsProperties;
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
        Wrapper<ActivityDetails> wrapper=new EntityWrapper<>();
        if(condition!=null && !condition.isEmpty()) {
            wrapper.like("title", condition);
        }
        List<ActivityDetails> activityDetails = activityDetailsService.selectList(wrapper);
        return activityDetails;
    }

    /**
     * 新增活动管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ActivityDetails activityDetails) {
        //UUID 自动生成主键id
        activityDetails.setId(UUIDUtils.getBase64UUID());
        activityDetails.setCreateTime(new Date());
        final Object userId = super.getSession().getAttribute("userId");
        activityDetails.setUid(Long.parseLong(userId.toString()));
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

    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture,@RequestParam("path") String path) {

        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        try {
            String fileSavePath = gunsProperties.getFileUploadPath()+path;
            picture.transferTo(new File(fileSavePath +File.separator+ pictureName));
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }


    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.GET, path = "/loadImg")
    @ResponseBody
    public byte[] loadImg(@RequestParam("filename") String filename,@RequestParam("path") String path) {

        String fileSavePath = gunsProperties.getFileUploadPath();
        try {
            String pathname = fileSavePath + path+File.separator+filename;
            return FileUtil.toByteArray(pathname);
        } catch (Exception e) {
            String pathname = fileSavePath + "noimage.png";
            return FileUtil.toByteArray(pathname);
        }

    }
}
