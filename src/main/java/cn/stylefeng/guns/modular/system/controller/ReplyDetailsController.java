package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IReplyDetailsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.ReplyDetails;

/**
 * 回复管理控制器
 *
 * @author fengshuonan
 * @Date 2019-01-13 00:24:13
 */
@Controller
@RequestMapping("/replyDetails")
public class ReplyDetailsController extends BaseController {

    private String PREFIX = "/system/replyDetails/";

    @Autowired
    private IReplyDetailsService replyDetailsService;

    /**
     * 跳转到回复管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "replyDetails.html";
    }

    /**
     * 跳转到添加回复管理
     */
    @RequestMapping("/replyDetails_add")
    public String replyDetailsAdd() {
        return PREFIX + "replyDetails_add.html";
    }

    /**
     * 跳转到修改回复管理
     */
    @RequestMapping("/replyDetails_update/{replyDetailsId}")
    public String replyDetailsUpdate(@PathVariable Integer replyDetailsId, Model model) {
        ReplyDetails replyDetails = replyDetailsService.selectById(replyDetailsId);
        model.addAttribute("item",replyDetails);
        LogObjectHolder.me().set(replyDetails);
        return PREFIX + "replyDetails_edit.html";
    }

    /**
     * 获取回复管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return replyDetailsService.selectList(null);
    }

    /**
     * 新增回复管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ReplyDetails replyDetails) {
        replyDetailsService.insert(replyDetails);
        return SUCCESS_TIP;
    }

    /**
     * 删除回复管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer replyDetailsId) {
        replyDetailsService.deleteById(replyDetailsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改回复管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ReplyDetails replyDetails) {
        replyDetailsService.updateById(replyDetails);
        return SUCCESS_TIP;
    }

    /**
     * 回复管理详情
     */
    @RequestMapping(value = "/detail/{replyDetailsId}")
    @ResponseBody
    public Object detail(@PathVariable("replyDetailsId") Integer replyDetailsId) {
        return replyDetailsService.selectById(replyDetailsId);
    }

    /**
     * 审核管理
     */
    @RequestMapping(value = "/review")
    @ResponseBody
    public Object delete(@RequestParam String replyDetailId,@RequestParam String type) {
        ReplyDetails t=new ReplyDetails();
        t.setId(replyDetailId);
        t.setReplyState(type.equals("tg")?1:-1);
        replyDetailsService.updateById(t);
        return SUCCESS_TIP;
    }
}
