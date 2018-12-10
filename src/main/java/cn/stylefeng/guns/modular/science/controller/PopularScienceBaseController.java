package cn.stylefeng.guns.modular.science.controller;

import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.core.shiro.ShiroKit;
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
import cn.stylefeng.guns.modular.system.model.PopularScienceBase;
import cn.stylefeng.guns.modular.science.service.IPopularScienceBaseService;

import java.util.Arrays;
import java.util.Date;

/**
 * 科普基地控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 17:44:24
 */
@Controller
@RequestMapping("/popularScienceBase")
public class PopularScienceBaseController extends BaseController {

    private String PREFIX = "/science/popularScienceBase/";

    @Autowired
    private IPopularScienceBaseService popularScienceBaseService;

    /**
     * 跳转到科普基地首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "popularScienceBase.html";
    }

    /**
     * 跳转到添加科普基地
     */
    @RequestMapping("/popularScienceBase_add")
    public String popularScienceBaseAdd() {
        return PREFIX + "popularScienceBase_add.html";
    }

    /**
     * 跳转到修改科普基地
     */
    @RequestMapping("/popularScienceBase_update/{popularScienceBaseId}")
    public String popularScienceBaseUpdate(@PathVariable String popularScienceBaseId, Model model) {
        PopularScienceBase popularScienceBase = popularScienceBaseService.selectById(popularScienceBaseId);
        model.addAttribute("item",popularScienceBase);
        LogObjectHolder.me().set(popularScienceBase);
        return PREFIX + "popularScienceBase_edit.html";
    }

    /**
     * 获取科普基地列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper<PopularScienceBase> wrapper=new EntityWrapper<>();
        wrapper.orderDesc(Arrays.asList("ind"));
        if(condition!=null && !condition.isEmpty()){
            wrapper.like("title",condition);
        }
        return popularScienceBaseService.selectList(wrapper);
    }

    /**
     * 新增科普基地
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PopularScienceBase popularScienceBase) {
        popularScienceBase.setId(UUIDUtils.getBase64UUID());
        popularScienceBase.setCreateTime(new Date());
        popularScienceBase.setUid(Long.parseLong(ShiroKit.getUser().getId().toString()));
        popularScienceBaseService.insert(popularScienceBase);
        return SUCCESS_TIP;
    }

    /**
     * 删除科普基地
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String popularScienceBaseId) {
        popularScienceBaseService.deleteById(popularScienceBaseId);
        return SUCCESS_TIP;
    }

    /**
     * 修改科普基地
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PopularScienceBase popularScienceBase) {
        popularScienceBaseService.updateById(popularScienceBase);
        return SUCCESS_TIP;
    }

    /**
     * 科普基地详情
     */
    @RequestMapping(value = "/detail/{popularScienceBaseId}")
    @ResponseBody
    public Object detail(@PathVariable("popularScienceBaseId") String popularScienceBaseId) {
        return popularScienceBaseService.selectById(popularScienceBaseId);
    }
}
