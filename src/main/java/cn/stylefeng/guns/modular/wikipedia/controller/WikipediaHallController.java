package cn.stylefeng.guns.modular.wikipedia.controller;

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
import cn.stylefeng.guns.modular.system.model.WikipediaHall;
import cn.stylefeng.guns.modular.wikipedia.service.IWikipediaHallService;

import java.util.Arrays;
import java.util.Date;

/**
 * 百科讲堂控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 18:01:59
 */
@Controller
@RequestMapping("/wikipediaHall")
public class WikipediaHallController extends BaseController {

    private String PREFIX = "/wikipedia/wikipediaHall/";

    @Autowired
    private IWikipediaHallService wikipediaHallService;

    /**
     * 跳转到百科讲堂首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "wikipediaHall.html";
    }

    /**
     * 跳转到添加百科讲堂
     */
    @RequestMapping("/wikipediaHall_add")
    public String wikipediaHallAdd() {
        return PREFIX + "wikipediaHall_add.html";
    }

    /**
     * 跳转到修改百科讲堂
     */
    @RequestMapping("/wikipediaHall_update/{wikipediaHallId}")
    public String wikipediaHallUpdate(@PathVariable String wikipediaHallId, Model model) {
        WikipediaHall wikipediaHall = wikipediaHallService.selectById(wikipediaHallId);
        model.addAttribute("item",wikipediaHall);
        LogObjectHolder.me().set(wikipediaHall);
        return PREFIX + "wikipediaHall_edit.html";
    }

    /**
     * 获取百科讲堂列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        Wrapper<WikipediaHall> wrapper=new EntityWrapper<>();
        wrapper.orderDesc(Arrays.asList("ind"));
        if(condition!=null && !condition.isEmpty()){
            wrapper.like("title",condition);
        }
        return wikipediaHallService.selectList(wrapper);
    }

    /**
     * 新增百科讲堂
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WikipediaHall wikipediaHall) {
        wikipediaHall.setId(UUIDUtils.getBase64UUID());
        wikipediaHall.setCreateTime(new Date());
        wikipediaHall.setUid(Long.parseLong(ShiroKit.getUser().getId().toString()));
        wikipediaHallService.insert(wikipediaHall);
        return SUCCESS_TIP;
    }

    /**
     * 删除百科讲堂
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String wikipediaHallId) {
        wikipediaHallService.deleteById(wikipediaHallId);
        return SUCCESS_TIP;
    }

    /**
     * 修改百科讲堂
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WikipediaHall wikipediaHall) {
        wikipediaHallService.updateById(wikipediaHall);
        return SUCCESS_TIP;
    }

    /**
     * 百科讲堂详情
     */
    @RequestMapping(value = "/detail/{wikipediaHallId}")
    @ResponseBody
    public Object detail(@PathVariable("wikipediaHallId") String wikipediaHallId) {
        return wikipediaHallService.selectById(wikipediaHallId);
    }
}
