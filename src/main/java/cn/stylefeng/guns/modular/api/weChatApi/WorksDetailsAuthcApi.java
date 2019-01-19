package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IAccountExtService;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.system.transfer.WorksDetailsDto;
import cn.stylefeng.guns.modular.system.transfer.WorksImgDetailsDto;
import cn.stylefeng.guns.modular.worksDetail.service.IGiveLikeDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IReplyDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksImgDetailsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.Request;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by lyc on 2018/12/22.
 */
@RestController
@RequestMapping("/weChatApi/authc/worksdetails")
public class WorksDetailsAuthcApi extends BaseController {
    @Autowired
    private IWorksDetailsService worksDetailsService;
    @Autowired
    private IGiveLikeDetailsService giveLikeDetailsService;
    @Autowired
    private IAccountExtService accountExtService;
    @Autowired
    private IReplyDetailsService replyDetailsService;
    @InitBinder
    public void intDate(WebDataBinder dataBinder){
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }
    /**
     * 获取我的作品管理列表
     */
    @RequestMapping(value = "/mylist")
    public Object mylist(@RequestParam(required=true,defaultValue="1") Integer page, @RequestParam(required=false,defaultValue="create_time") String orderBy) {
        String orderBycolnum = "create_time";
        WorksDetails worksDetails = new WorksDetails();
        long uid = getUserId().longValue();
        Page<WorksDetails> pages =  new Page<>(page,12);
        worksDetails.setUid(uid);
        EntityWrapper<WorksDetails> worksDetailsEntityWrapper = new EntityWrapper<>();
        worksDetailsEntityWrapper.where(worksDetails.getUid()!=null," uid ={0} and details_delete=0 ",worksDetails.getUid())
                .orderBy(orderBycolnum);
        Page<WorksDetails> worksDetailsPage = worksDetailsService.selectPage(pages, worksDetailsEntityWrapper);
        return  new SuccessResponseData(worksDetailsPage);
    }

    private Integer getUserId() {
        Object openId = super.getSession().getAttribute(AuthUtil.OPENID);
        Wrapper<AccountExt> warpper=new EntityWrapper<>();
        warpper.eq("webchat_open_id",openId.toString());
        AccountExt accountExt = accountExtService.selectOne(warpper);
        if(accountExt==null){
            throw new ServiceException(BizExceptionEnum.TOKEN_EXPIRED);
        }
        return accountExt.getId();
    }

    @RequestMapping(value = "/delete/{worksDetailsId}",method = RequestMethod.GET)
    public Object delete(@PathVariable String worksDetailsId){
        WorksDetails worksDetails = worksDetailsService.selectById(worksDetailsId);
        long uid = getUserId().longValue();
        // 不是本人作品
        if(uid!=worksDetails.getUid().longValue()){
//            throw new ServiceException(702,"只能删除本人作品");
            return new ErrorResponseData(403,"无权操作");
        }
        worksDetails.setDetailsDelete(1);
        boolean b = worksDetailsService.updateAllColumnById(worksDetails);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(WorksDetailsDto worksDetailsDto){

        if(worksDetailsDto==null || worksDetailsDto.getWorksImgDetailsList()==null || worksDetailsDto.getWorksImgDetailsList().size()==0){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        WorksImgDetailsDto worksImgDetailsDto = worksDetailsDto.getWorksImgDetailsList().get(0);
        if(ToolUtil.isOneEmpty(worksImgDetailsDto,worksImgDetailsDto.getDetailImg())){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        if (ToolUtil.isOneEmpty(worksDetailsDto,worksDetailsDto.getWorksTitle(),worksDetailsDto.getPohtoTime(),
                worksDetailsDto.getWeather(),worksDetailsDto.getAddress(),worksDetailsDto.getTakenAuthor(),
                worksDetailsDto.getTakenTool(),worksDetailsDto.getContent(),worksDetailsDto.getAnswerOne())){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        long uid = getUserId().longValue();
        //TODO 判断当前人是否能上传作品
        AccountExt accountExt = accountExtService.selectById(uid);
        if(accountExt!=null&&accountExt.getBanPost()==1){
            throw new ServiceException(701,"禁止上传作品");
        }
        worksDetailsDto.setUid(uid);
        worksDetailsService.insertWorksDetailsDto(worksDetailsDto);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/update/{worksDetailsId}",method = RequestMethod.POST)
    public Object update(@PathVariable String worksDetailsId,@RequestBody WorksDetailsDto worksDetailsDto){
        if(worksDetailsDto==null  || worksDetailsDto.getWorksImgDetailsList().size()==0){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        WorksImgDetailsDto worksImgDetailsDto = worksDetailsDto.getWorksImgDetailsList().get(0);
        if(ToolUtil.isOneEmpty(worksImgDetailsDto,worksImgDetailsDto.getDetailImg())){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        if (ToolUtil.isOneEmpty(worksDetailsDto,worksDetailsDto.getWorksTitle(),worksDetailsDto.getPohtoTime(),
                worksDetailsDto.getWeather(),worksDetailsDto.getAddress(),worksDetailsDto.getTakenAuthor(),
                worksDetailsDto.getTakenTool(),worksDetailsDto.getContent(),worksDetailsDto.getAnswerOne())){
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        WorksDetails worksDetails = worksDetailsService.selectById(worksDetailsId);
        long uid = getUserId().longValue();
        // 不是本人作品
        if(uid!=worksDetails.getUid().longValue()){
            return new ErrorResponseData(403,"无权操作");
//            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        worksDetailsDto.setId(worksDetailsId);
        worksDetailsDto.setUid(uid);
        worksDetailsService.updateWorksDetailsDto(worksDetailsDto);
        return SUCCESS_TIP;
    }

    /**
     *
     * @param worksDetailsId 作品
     * @param auditType PASS 通过   REJECT 不通过
     * @return
     */
    @RequestMapping(value = "/auditWorksDetails/{worksDetailsId}_{auditType}")
    public Object auditWorksDetails(@PathVariable String worksDetailsId,@PathVariable String auditType){
        WorksDetails worksDetails = worksDetailsService.selectById(worksDetailsId);
        if(worksDetails==null){
            return new ErrorResponseData(401,"作品查询错误");
        }
        WorksDetails worksDetail = new WorksDetails();
        worksDetail.setId(worksDetailsId);
        if("REJECT".equals(auditType.toUpperCase())){
            worksDetail.setState(1);
        }else{
            worksDetail.setState(-1);
        }
        worksDetailsService.updateById(worksDetail);
        return SUCCESS_TIP;
    }

    /**
     * 点赞
     * @param worksDetailsId
     * @return
     */
    @RequestMapping(value = "/addGiveLike/{worksDetailsId}",method = RequestMethod.GET)
    public Object addGiveLike(@PathVariable String worksDetailsId){
        long uid = getUserId().longValue();
        // 判断当前人员一天三次的点赞是否用尽
        AccountExt accountExt = accountExtService.selectById(uid);
        if(accountExt==null||accountExt.getBanGiveLike()==1){
            return new ErrorResponseData(701,"禁止点赞");
//            throw new ServiceException(701,"禁止点赞");
        }
        // 判断当前是否已经点赞
        EntityWrapper<GiveLikeDetails> giveLikeDetailsWrapper = new EntityWrapper<>();
        GiveLikeDetails giveLikeDetails = new GiveLikeDetails();
        giveLikeDetails.setBusinessId(worksDetailsId);
        giveLikeDetails.setModel(BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
        giveLikeDetails.setUid(uid);
        giveLikeDetailsWrapper.setEntity(giveLikeDetails);
        List<GiveLikeDetails> giveLikeDetailss = giveLikeDetailsService.selectList(giveLikeDetailsWrapper);
        if(giveLikeDetailss!=null && giveLikeDetailss.size()>0){
            // 已经点赞不能在进行点赞
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(702,"今天点赞达到上限");
        }
        //点赞
        giveLikeDetails.setCreateTime(new Date());
        giveLikeDetailsService.insert(giveLikeDetails);
        return SUCCESS_TIP;
    }

    /**
     *  添加回帖信息
     * @param worksDetailsId
     * @param parentId 可以为空
     * @return
     */
    @RequestMapping(value = "/addReplyDetails/{worksDetailsId}_{parentId}",method=RequestMethod.POST)
    public Object addReplyDetails(@PathVariable String worksDetailsId,@PathVariable(required = false) String parentId,@RequestBody ReplyDetails replyDetails){
        if(ToolUtil.isOneEmpty(replyDetails,replyDetails.getBusinessId(),replyDetails.getContent())){
            // 数据不全
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        long uid = getUserId().longValue();
        // 判断当前人员一天三次的点赞是否用尽
        AccountExt accountExt = accountExtService.selectById(uid);
        if(accountExt==null||accountExt.getBanReply()==1){
            throw new ServiceException(701,"禁止回复");
        }
        replyDetails.setModel(BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
        replyDetails.setCreateTime(new Date());
        replyDetails.setReplyState(0);
        replyDetails.setGiveLikeNumber(0);
        if(ToolUtil.isNotEmpty(parentId)) {
            replyDetails.setParentId(parentId);
        }else{
            replyDetails.setParentId("0");
        }
        replyDetails.setId(UUIDUtils.getBase64UUID());
        replyDetails.setUid(uid);
        replyDetailsService.insert(replyDetails);
        return SUCCESS_TIP;
    }

    /**
     *
     * @param replyDetailsId 回帖主键ID
     * @param auditType PASS 通过   REJECT 不通过
     * @return
     */
    @RequestMapping(value = "/auditReplyDetails/{replyDetailsId}_{auditType}")
    public Object auditReplyDetails(@PathVariable String replyDetailsId,@PathVariable String auditType){
        ReplyDetails replyDetailsOld = replyDetailsService.selectById(replyDetailsId);
        if(replyDetailsOld==null){
            // 数据不全
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            return new ErrorResponseData(400,"数据错误");
        }
        ReplyDetails replyDetails = new ReplyDetails();
        replyDetails.setId(replyDetailsId);
        replyDetails.setBusinessId(replyDetailsOld.getBusinessId());
        replyDetails.setModel(replyDetailsOld.getModel());
        if("REJECT".equals(auditType.toUpperCase())){
            replyDetails.setReplyState(1);
        }else{
            replyDetails.setReplyState(-1);
        }
        replyDetailsService.updateById(replyDetails);
        return SUCCESS_TIP;
    }

}
