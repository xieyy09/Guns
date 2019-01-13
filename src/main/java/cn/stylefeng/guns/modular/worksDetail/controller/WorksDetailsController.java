package cn.stylefeng.guns.modular.worksDetail.controller;

import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.guns.modular.system.model.WorksImgDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksImgDetailsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;

import java.util.List;

/**
 * 作品管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-22 13:05:50
 */
@Controller
@RequestMapping("/worksDetails")
public class WorksDetailsController extends BaseController {

    private String PREFIX = "/worksDetail/worksDetails/";

    @Autowired
    private IWorksDetailsService worksDetailsService;
    @Autowired
    private IWorksImgDetailsService worksImgDetailsService;
    /**
     * 跳转到作品管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "worksDetails.html";
    }

    /**
     * 跳转到添加作品管理
     */
    @RequestMapping("/worksDetails_add")
    public String worksDetailsAdd() {
        return PREFIX + "worksDetails_add.html";
    }

    /**
     * 跳转到修改作品管理
     */
    @RequestMapping("/worksDetails_update/{worksDetailsId}")
    public String worksDetailsUpdate(@PathVariable String worksDetailsId, Model model) {
        WorksDetails worksDetails = worksDetailsService.selectById(worksDetailsId);
        model.addAttribute("item",worksDetails);
        EntityWrapper<WorksImgDetails> worksImgDetailWrapper = new EntityWrapper<>();
        WorksImgDetails worksImgDetails = new WorksImgDetails();
        worksImgDetails.setWorksDetailsId(worksDetailsId);
        worksImgDetailWrapper.setEntity(worksImgDetails);
        List<WorksImgDetails> worksImgDetailsList = worksImgDetailsService.selectList(worksImgDetailWrapper);
        model.addAttribute("itemImg",worksImgDetailsList);
        LogObjectHolder.me().set(worksDetails);
        return PREFIX + "worksDetails_edit.html";
    }

    /**
     * 获取作品管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper<WorksDetails> wrapper=new EntityWrapper<>();
        if(condition!=null && !condition.isEmpty()) {
            wrapper.like("worksTitle", condition);
        }
        return worksDetailsService.selectList(wrapper);

    }

    /**
     * 新增作品管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorksDetails worksDetails) {
        worksDetailsService.insert(worksDetails);
        return SUCCESS_TIP;
    }

    /**
     * 删除作品管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer worksDetailsId) {
        worksDetailsService.deleteById(worksDetailsId);
        return SUCCESS_TIP;
    }
    /**
     * 审核作品管理
     */
    @RequestMapping(value = "/review")
    @ResponseBody
    public Object delete(@RequestParam String worksDetailsId,@RequestParam String type) {
        WorksDetails t=new WorksDetails();
        t.setId(worksDetailsId);
        t.setState(type.equals("tg")?1:-1);
        worksDetailsService.updateById(t);
        return SUCCESS_TIP;
    }
    /**
     * 修改作品管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorksDetails worksDetails) {
        worksDetailsService.updateById(worksDetails);
        return SUCCESS_TIP;
    }

    /**
     * 作品管理详情
     */
    @RequestMapping(value = "/detail/{worksDetailsId}")
    @ResponseBody
    public Object detail(@PathVariable("worksDetailsId") Integer worksDetailsId) {
        return worksDetailsService.selectById(worksDetailsId);
    }
}
