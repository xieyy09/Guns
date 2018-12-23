package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.system.transfer.WorksDetailsDto;
import cn.stylefeng.guns.modular.system.transfer.WorksImgDetailsDto;
import cn.stylefeng.guns.modular.worksDetail.service.IGiveLikeDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IReplyDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksImgDetailsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by lyc on 2018/12/22.
 */
@RestController
@RequestMapping("/gunsApi/worksDetails")
public class ApiWorksDetailsController extends BaseController {
    @Autowired
    private IWorksDetailsService worksDetailsService;
    @Autowired
    private IWorksImgDetailsService worksImgDetailsService;
    @Autowired
    private IGiveLikeDetailsService giveLikeDetailsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IReplyDetailsService replyDetailsService;
    /**
     * 获取作品管理列表
     */
    @RequestMapping(value = "/list")
    public Object list(String condition) {
        String orderBycolnum = "create_time";
        if("giveLike".toLowerCase().equals(condition.toLowerCase())){
            orderBycolnum ="give_like_number";
        }
        WorksDetails worksDetails = new WorksDetails();
        worksDetails.setState(1);
        EntityWrapper<WorksDetails> worksDetailsEntityWrapper = new EntityWrapper<>();
        worksDetailsEntityWrapper.where(worksDetails.getState()!=null," state ={0}",worksDetails.getState())
                .orderBy(orderBycolnum);
        return worksDetailsService.selectList(worksDetailsEntityWrapper);
    }

    /**
     * 获取我的作品管理列表
     */
    @RequestMapping(value = "/mylist")
    public Object mylist(String condition) {
        String orderBycolnum = "create_time";
        WorksDetails worksDetails = new WorksDetails();
        ShiroUser user = ShiroKit.getUser();
        if(user==null){
            throw new ServiceException(BizExceptionEnum.TOKEN_EXPIRED);
        }
        long uid = user.getId().longValue();
        worksDetails.setUid(uid);
        EntityWrapper<WorksDetails> worksDetailsEntityWrapper = new EntityWrapper<>();
        worksDetailsEntityWrapper.where(worksDetails.getUid()!=null," uid ={0}",worksDetails.getUid())
                .orderBy(orderBycolnum);
        return worksDetailsService.selectList(worksDetailsEntityWrapper);
    }

    @RequestMapping(value = "/delete/{worksDetailsId}")
    public Object delete(@PathVariable String worksDetailsId){
        WorksDetails worksDetails = worksDetailsService.selectById(worksDetailsId);
        long uid = ShiroKit.getUser().getId().longValue();
        // 不是本人作品
        if(uid!=worksDetails.getUid().longValue()){
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        boolean b = worksDetailsService.deleteById(worksDetailsId);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/add")
    public Object add(@RequestBody WorksDetailsDto worksDetailsDto){
        //TODO 判断当前人是否能上传作品


        if(worksDetailsDto==null || worksDetailsDto.getWorksImgDetailsList().size()==0){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        WorksImgDetailsDto worksImgDetailsDto = worksDetailsDto.getWorksImgDetailsList().get(0);
        if(ToolUtil.isOneEmpty(worksImgDetailsDto,worksImgDetailsDto.getDetailImg())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ToolUtil.isOneEmpty(worksDetailsDto,worksDetailsDto.getWorksTitle(),worksDetailsDto.getPohtoTime(),
                worksDetailsDto.getWeather(),worksDetailsDto.getAddress(),worksDetailsDto.getTakenAuthor(),
                worksDetailsDto.getTakenTool(),worksDetailsDto.getContent(),worksDetailsDto.getAnswerOne())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        worksDetailsDto.setUid(ShiroKit.getUser().getId().longValue());
        worksDetailsService.insertWorksDetailsDto(worksDetailsDto);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/update/{worksDetailsId}")
    public Object update(@PathVariable String worksDetailsId,@RequestBody WorksDetailsDto worksDetailsDto){
        if(worksDetailsDto==null  || worksDetailsDto.getWorksImgDetailsList().size()==0){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        WorksImgDetailsDto worksImgDetailsDto = worksDetailsDto.getWorksImgDetailsList().get(0);
        if(ToolUtil.isOneEmpty(worksImgDetailsDto,worksImgDetailsDto.getDetailImg())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ToolUtil.isOneEmpty(worksDetailsDto,worksDetailsDto.getWorksTitle(),worksDetailsDto.getPohtoTime(),
                worksDetailsDto.getWeather(),worksDetailsDto.getAddress(),worksDetailsDto.getTakenAuthor(),
                worksDetailsDto.getTakenTool(),worksDetailsDto.getContent(),worksDetailsDto.getAnswerOne())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        WorksDetails worksDetails = worksDetailsService.selectById(worksDetailsId);
        long uid = ShiroKit.getUser().getId().longValue();
        // 不是本人作品
        if(uid!=worksDetails.getUid().longValue()){
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
        worksDetailsDto.setId(worksDetailsId);
        worksDetailsDto.setUid(uid);
        worksDetailsService.updateWorksDetailsDto(worksDetailsDto);
        return SUCCESS_TIP;
    }

    /**
     *  获取作品
     * @param worksDetailsId
     * @return
     */
    @RequestMapping(value = "/get/{worksDetailsId}")
    public Object get(@PathVariable String worksDetailsId){
        WorksDetails worksDetails = worksDetailsService.selectById(worksDetailsId);
        if(worksDetails==null){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        // 获取图片信息
        EntityWrapper<WorksImgDetails> worksImgDetailWrapper = new EntityWrapper<>();
        WorksImgDetails worksImgDetails = new WorksImgDetails();
        worksImgDetails.setWorksDetailsId(worksDetailsId);
        worksImgDetailWrapper.setEntity(worksImgDetails);
        List<WorksImgDetails> worksImgDetailsList = worksImgDetailsService.selectList(worksImgDetailWrapper);
        WorksDetailsDto worksDetailsDto = new WorksDetailsDto();
        BeanUtils.copyProperties(worksDetails, worksDetailsDto);
        if(worksImgDetailsList!=null && worksImgDetailsList.size()>0) {
            List<WorksImgDetailsDto> worksImgDetailsDtoList = new ArrayList<>();
            for (WorksImgDetails worksImgDetails_temp : worksImgDetailsList) {
                WorksImgDetailsDto imgDto = new WorksImgDetailsDto();
                ToolUtil.copyProperties(worksImgDetails_temp, imgDto);
                worksImgDetailsDtoList.add(imgDto);
            }
            worksDetailsDto.setWorksImgDetailsList(worksImgDetailsDtoList);
        }
        // 获取发贴人信息
        final User user = userService.selectById(worksDetails.getUid().intValue());
        Map resultMap  = new HashMap();
        resultMap.put("name",user.getName());
        resultMap.put("avatar",user.getAvatar());
        resultMap.put("worksDetails",worksDetailsDto);
        SuccessResponseData responseData = new SuccessResponseData();
        responseData.setData(resultMap);
        responseData.setCode(ResponseData.DEFAULT_SUCCESS_CODE);
        responseData.setMessage(ResponseData.DEFAULT_SUCCESS_MESSAGE);
        return responseData;
    }

    /**
     * 获取点赞
     * @param worksDetailsId
     * @return
     */
    @RequestMapping(value = "/getGiveLike/{worksDetailsId}")
    public Object getGiveLike(@PathVariable String worksDetailsId){
        // 获取点赞信息
        EntityWrapper<GiveLikeDetails> giveLikeDetailsWrapper = new EntityWrapper<>();
        GiveLikeDetails giveLikeDetails = new GiveLikeDetails();
        giveLikeDetails.setBusinessId(worksDetailsId);
        giveLikeDetails.setModel(BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
        giveLikeDetailsWrapper.setEntity(giveLikeDetails);
        List<GiveLikeDetails> giveLikeDetailss = giveLikeDetailsService.selectList(giveLikeDetailsWrapper);
        // 根据用户ID 获取用户信息

        SuccessResponseData responseData = new SuccessResponseData();
        responseData.setData(giveLikeDetailss);
        responseData.setCode(ResponseData.DEFAULT_SUCCESS_CODE);
        responseData.setMessage(ResponseData.DEFAULT_SUCCESS_MESSAGE);
        return responseData;
    }

    /**
     * 获取回帖信息
     * @param worksDetailsId
     * @return
     */
    @RequestMapping(value = "/getReplyDetails/{worksDetailsId}")
    public Object getReplyDetails(@PathVariable String worksDetailsId,@RequestParam(required=false) String state){
        // 获取回帖信息
        Wrapper<ReplyDetails> wrapper = new EntityWrapper();
        ReplyDetails replyDetails = new ReplyDetails();
        replyDetails.setModel(BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
        replyDetails.setBusinessId(worksDetailsId);
        wrapper.where("model = {0} and business_id = {1}",replyDetails.getModel(),replyDetails.getBusinessId())
                .orderBy("create_time",false);
        List<ReplyDetails> replyDetailss = replyDetailsService.selectList(wrapper);
//        // 根据用户ID 获取用户信息

        SuccessResponseData responseData = new SuccessResponseData();
        responseData.setData(replyDetailss);
        responseData.setCode(ResponseData.DEFAULT_SUCCESS_CODE);
        responseData.setMessage(ResponseData.DEFAULT_SUCCESS_MESSAGE);
        return responseData;
    }

    /**
     * 点赞
     * @param worksDetailsId
     * @return
     */
    @RequestMapping(value = "/addGiveLike/{worksDetailsId}")
    public Object addGiveLike(@PathVariable String worksDetailsId){
        long uid = ShiroKit.getUser().getId().longValue();
        // 判断当前人员一天三次的点赞是否用尽


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
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
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
    @RequestMapping(value = "/addReplyDetails/{worksDetailsId}_{parentId}")
    public Object addReplyDetails(@PathVariable String worksDetailsId,@PathVariable String parentId,@RequestBody ReplyDetails replyDetails){
        if(ToolUtil.isOneEmpty(replyDetails,replyDetails.getBusinessId(),replyDetails.getContent())){
            // 数据不全
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        replyDetails.setModel(BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
        replyDetails.setCreateTime(new Date());
        replyDetails.setReplyState(0);
        replyDetails.setGiveLikeNumber(0);
        if(ToolUtil.isEmpty(parentId)) {
            replyDetails.setParentId(parentId);
        }else{
            replyDetails.setParentId("0");
        }
        replyDetails.setId(UUIDUtils.getBase64UUID());
        replyDetails.setUid(ShiroKit.getUser().getId().longValue());
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
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
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
