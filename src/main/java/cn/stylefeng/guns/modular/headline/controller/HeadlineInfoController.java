package cn.stylefeng.guns.modular.headline.controller;

import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.headline.service.IHeadlineInfoService;
import cn.stylefeng.guns.modular.system.model.HeadlineInfo;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-01-09 20:39:14
 */
@Controller
@RequestMapping("/headlineInfo")
public class HeadlineInfoController extends BaseController {

    private String PREFIX = "/headline/headlineInfo/";

    @Autowired
    private IHeadlineInfoService headlineInfoService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "headlineInfo.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/headlineInfo_add")
    public String headlineInfoAdd() {
        return PREFIX + "headlineInfo_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/headlineInfo_update/{headlineInfoId}")
    public String headlineInfoUpdate(@PathVariable String headlineInfoId, Model model) {
        HeadlineInfo headlineInfo = headlineInfoService.selectById(headlineInfoId);
        model.addAttribute("item",headlineInfo);
        LogObjectHolder.me().set(headlineInfo);
        return PREFIX + "headlineInfo_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper<HeadlineInfo> wrapper=new EntityWrapper<>();
        if(condition!=null && !condition.isEmpty()) {
            wrapper.like("title", condition);
        }
        return headlineInfoService.selectList(wrapper);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(HeadlineInfo headlineInfo) {
        headlineInfo.setId(UUIDUtils.getBase64UUID());
        headlineInfo.setCreatetime(new Date());
        headlineInfo.setCreateuser(ShiroKit.getUser().getId());
        wrapperHead(headlineInfo);
        headlineInfoService.insert(headlineInfo);
        return SUCCESS_TIP;
    }

    private void wrapperHead(HeadlineInfo headlineInfo) {
        if(headlineInfo.getModule().equals("other") && StringUtils.isEmpty(headlineInfo.getUrl())){
            throw new ServiceException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        String url="";
        switch (headlineInfo.getModule()){
            case "activity":
                //活动
                url="/weChatApi/activity/info?id="+headlineInfo.getOid();
                break;
            case "worksDetail":
                //作品
                url="/weChatApi/worksdetails/get/"+headlineInfo.getOid();
                break;
            case "science":
                //科普基地
                url="/weChatApi/science/info?id="+headlineInfo.getOid();
                break;
            case "champion":
                //擂主
                url="/weChatApi/champio/info?id="+headlineInfo.getOid();
                break;
        }
        if(!headlineInfo.getModule().equals("other")){
            headlineInfo.setUrl(url);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String headlineInfoId) {
        headlineInfoService.deleteById(headlineInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(HeadlineInfo headlineInfo) {
        wrapperHead(headlineInfo);
        headlineInfoService.updateById(headlineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{headlineInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("headlineInfoId") String headlineInfoId) {
        return headlineInfoService.selectById(headlineInfoId);
    }
}
