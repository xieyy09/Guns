package cn.stylefeng.guns.modular.worksDetail.controller;

import cn.stylefeng.guns.modular.activity.service.IActivityDetailsService;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IAccountExtService;
import cn.stylefeng.guns.modular.system.transfer.WorksDetailsDto;
import cn.stylefeng.guns.modular.system.transfer.WorksImgDetailsDto;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksImgDetailsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IActivityDetailsService activityDetailsService;

    @Autowired
    private IAccountExtService accountExtService;
    
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
    public String worksDetailsAdd(Model model) {
        Map<String, Object> param=new HashMap<>();
        List<ActivityDetails> activityDetails = activityDetailsService.selectByMap(param);
        model.addAttribute("activityDetails",activityDetails);
       // param.put("")
        Wrapper<AccountExt> accountExtWrapper= new EntityWrapper<AccountExt>();
        accountExtWrapper.where("webchat_open_id!=''");
        List<AccountExt> accountExts = accountExtService.selectList(accountExtWrapper);
        model.addAttribute("accountExts",accountExts);
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
            wrapper.like("works_title", condition);
        }
        return worksDetailsService.selectList(wrapper);

    }

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody WorksDetailsDto worksDetailsDto){

        if(worksDetailsDto==null || worksDetailsDto.getWorksImgDetailsList()==null || worksDetailsDto.getWorksImgDetailsList().size()==0){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(500,"数据错误");
        }
        WorksImgDetailsDto worksImgDetailsDto = worksDetailsDto.getWorksImgDetailsList().get(0);
        if(ToolUtil.isOneEmpty(worksImgDetailsDto,worksImgDetailsDto.getDetailImg())){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(500,"数据错误");
        }
        if (ToolUtil.isOneEmpty(worksDetailsDto,worksDetailsDto.getWorksTitle(),worksDetailsDto.getPohtoTime() ,worksDetailsDto.getAddress(),worksDetailsDto.getTakenAuthor(),
                worksDetailsDto.getTakenTool(),worksDetailsDto.getContent(),worksDetailsDto.getAnswerOne())){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(500,"数据错误");
        }
        worksDetailsService.insertWorksDetailsDto(worksDetailsDto);
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
