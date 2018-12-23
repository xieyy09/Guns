package cn.stylefeng.guns.modular.worksDetail.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.GiveLikeDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IGiveLikeDetailsService;

/**
 * 点赞控制器
 *
 * @author fengshuonan
 * @Date 2018-12-22 13:13:14
 */
@Controller
@RequestMapping("/giveLikeDetails")
public class GiveLikeDetailsController extends BaseController {

    private String PREFIX = "/worksDetail/giveLikeDetails/";

    @Autowired
    private IGiveLikeDetailsService giveLikeDetailsService;

    /**
     * 跳转到点赞首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "giveLikeDetails.html";
    }

    /**
     * 跳转到添加点赞
     */
    @RequestMapping("/giveLikeDetails_add")
    public String giveLikeDetailsAdd() {
        return PREFIX + "giveLikeDetails_add.html";
    }

    /**
     * 跳转到修改点赞
     */
    @RequestMapping("/giveLikeDetails_update/{giveLikeDetailsId}")
    public String giveLikeDetailsUpdate(@PathVariable Integer giveLikeDetailsId, Model model) {
        GiveLikeDetails giveLikeDetails = giveLikeDetailsService.selectById(giveLikeDetailsId);
        model.addAttribute("item",giveLikeDetails);
        LogObjectHolder.me().set(giveLikeDetails);
        return PREFIX + "giveLikeDetails_edit.html";
    }

    /**
     * 获取点赞列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return giveLikeDetailsService.selectList(null);
    }

    /**
     * 新增点赞
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(GiveLikeDetails giveLikeDetails) {
        giveLikeDetailsService.insert(giveLikeDetails);
        return SUCCESS_TIP;
    }

    /**
     * 删除点赞
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer giveLikeDetailsId) {
        giveLikeDetailsService.deleteById(giveLikeDetailsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改点赞
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(GiveLikeDetails giveLikeDetails) {
        giveLikeDetailsService.updateById(giveLikeDetails);
        return SUCCESS_TIP;
    }

    /**
     * 点赞详情
     */
    @RequestMapping(value = "/detail/{giveLikeDetailsId}")
    @ResponseBody
    public Object detail(@PathVariable("giveLikeDetailsId") Integer giveLikeDetailsId) {
        return giveLikeDetailsService.selectById(giveLikeDetailsId);
    }
}
