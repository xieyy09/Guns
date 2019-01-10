package cn.stylefeng.guns.modular.champion.controller;

import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.science.service.IPopularScienceBaseService;
import cn.stylefeng.guns.modular.system.model.PopularScienceBase;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.guns.modular.champion.service.IChampionService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 擂主管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 13:26:41
 */
@Controller
@RequestMapping("/champion")
public class ChampionController extends BaseController {

    private String PREFIX = "/champion/champion/";

    @Autowired
    private IChampionService championService;
    @Autowired
    private IPopularScienceBaseService popularScienceBaseService;
    /**
     * 跳转到擂主管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "champion.html";
    }

    /**
     * 跳转到添加擂主管理
     */
    @RequestMapping("/champion_add")
    public String championAdd(Model model) {
        Map<String, Object> param=new HashMap<>();
        List<PopularScienceBase> popularScienceBases = popularScienceBaseService.selectByMap(param);
        model.addAttribute("psb",popularScienceBases);
        return PREFIX + "champion_add.html";
    }

    /**
     * 跳转到修改擂主管理
     */
    @RequestMapping("/champion_update/{championId}")
    public String championUpdate(@PathVariable String championId, Model model) {
        Champion champion = championService.selectById(championId);
        Map<String, Object> param=new HashMap<>();
        List<PopularScienceBase> popularScienceBases = popularScienceBaseService.selectByMap(param);
        model.addAttribute("psb",popularScienceBases);
        model.addAttribute("item",champion);
        LogObjectHolder.me().set(champion);
        return PREFIX + "champion_edit.html";
    }

    /**
     * 获取擂主管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper<Champion> wrapper=new EntityWrapper<>();
        if(condition!=null && !condition.isEmpty()) {
            wrapper.like("champion_name", condition);
        }
        return championService.selectList(wrapper);
    }

    /**
     * 新增擂主管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Champion champion) {
        champion.setId(UUIDUtils.getBase64UUID());
        champion.setCreateTime(new Date());
        champion.setCreateUid(Long.parseLong(ShiroKit.getUser().getId().toString()));
        if(!StringUtils.isEmpty(champion.getPhone())){
            Wrapper<Champion> warpper=new EntityWrapper<>();
            warpper.eq("phone",champion.getPhone());
            int i = championService.selectCount(warpper);
            if(i>0){
                throw new ServiceException(BizExceptionEnum.SPHONE_ERROR);
            }
        }
        championService.insert(champion);
        return SUCCESS_TIP;
    }

    /**
     * 删除擂主管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String championId) {
        championService.deleteById(championId);
        return SUCCESS_TIP;
    }

    /**
     * 修改擂主管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Champion champion) {
        if(!StringUtils.isEmpty(champion.getPhone())){
            Wrapper<Champion> warpper=new EntityWrapper<>();
            warpper.eq("phone",champion.getPhone()).notIn("id",champion.getId());
            int i = championService.selectCount(warpper);
            if(i>0){
                throw new ServiceException(BizExceptionEnum.SPHONE_ERROR);
            }
        }
        championService.updateById(champion);
        return SUCCESS_TIP;
    }

    /**
     * 擂主管理详情
     */
    @RequestMapping(value = "/detail/{championId}")
    @ResponseBody
    public Object detail(@PathVariable("championId") String championId) {
        return championService.selectById(championId);
    }
}
