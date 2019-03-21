package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.modular.champion.service.IChampionService;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;
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
import cn.stylefeng.guns.modular.system.model.ChampionReply;
import cn.stylefeng.guns.modular.system.service.IChampionReplyService;

import java.util.Date;
import java.util.List;

/**
 * championReply控制器
 *
 * @author fengshuonan
 * @Date 2019-02-27 17:03:07
 */
@Controller
@RequestMapping("/championReply")
public class ChampionReplyController extends BaseController {

    private String PREFIX = "/system/championReply/";

    @Autowired
    private IChampionReplyService championReplyService;
    @Autowired
    private IWorksDetailsService worksDetailsService;
    @Autowired
    private IChampionService championService;
    /**
     * 跳转到championReply首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "championReply.html";
    }

    /**
     * 跳转到添加championReply
     */
    @RequestMapping("/championReply_add")
    public String championReplyAdd(Model model) {
        Wrapper<WorksDetails> wrapper=new EntityWrapper<>();
        wrapper.where("state=1 and details_delete=0 ").orderBy("create_time",false);
        List<WorksDetails> worksDetails = worksDetailsService.selectList(wrapper);
        String businessTitle = "";
        if(worksDetails!=null && worksDetails.size()>0){
            businessTitle =  worksDetails.get(0).getWorksTitle();
        }
        model.addAttribute("businessTitle",businessTitle);
        model.addAttribute("worksDetails",worksDetails);
        Wrapper<Champion> championWrapper=new EntityWrapper<>();
        championWrapper.where("state=1");
        List<Champion> champions = championService.selectList(championWrapper);
        String championName = "";
        if(champions!=null && champions.size()>0){
            championName =  champions.get(0).getChampionName();
        }
        model.addAttribute("championName",championName);
        model.addAttribute("champions",champions);
        return PREFIX + "championReply_add.html";
    }

    /**
     * 跳转到修改championReply
     */
    @RequestMapping("/championReply_update/{championReplyId}")
    public String championReplyUpdate(@PathVariable String championReplyId, Model model) {
        ChampionReply championReply = championReplyService.selectById(championReplyId);
        model.addAttribute("item",championReply);
        LogObjectHolder.me().set(championReply);
        return PREFIX + "championReply_edit.html";
    }

    /**
     * 获取championReply列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return championReplyService.selectList(null);
    }

    /**
     * 新增championReply
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ChampionReply championReply) {
        championReply.setCreateTime(new Date());
        championReply.setId(UUIDUtils.getBase64UUID());
        championReply.setModel("worksDetail");
        championReplyService.insertChampionReply(championReply);
        return SUCCESS_TIP;
    }

    /**
     * 删除championReply
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer championReplyId) {
        championReplyService.deleteById(championReplyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改championReply
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ChampionReply championReply) {
        championReplyService.updateById(championReply);
        return SUCCESS_TIP;
    }

    /**
     * championReply详情
     */
    @RequestMapping(value = "/detail/{championReplyId}")
    @ResponseBody
    public Object detail(@PathVariable("championReplyId") Integer championReplyId) {
        return championReplyService.selectById(championReplyId);
    }
}
