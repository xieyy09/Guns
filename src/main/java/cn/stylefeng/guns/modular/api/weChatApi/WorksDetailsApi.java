package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.system.transfer.WorksDetailsDto;
import cn.stylefeng.guns.modular.system.transfer.WorksImgDetailsDto;
import cn.stylefeng.guns.modular.worksDetail.service.IGiveLikeDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IReplyDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksImgDetailsService;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyc on 2018/12/23.
 */
@RestController
@RequestMapping("/weChatApi/worksdetails")
@Slf4j
public class WorksDetailsApi {
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
    public Page<WorksDetails> list(@RequestParam(required=true,defaultValue="1") Integer page, @RequestParam(required=false,defaultValue="create_time") String condition) {
        String orderBycolnum = "create_time";
        if(!StringUtils.isEmpty(condition) && "giveLike".toLowerCase().equals(condition.toLowerCase())){
            orderBycolnum ="give_like_number";
        }
        Page<WorksDetails> pages =  new Page<>(page,20);
        WorksDetails worksDetails = new WorksDetails();
        worksDetails.setState(1);
        EntityWrapper<WorksDetails> worksDetailsEntityWrapper = new EntityWrapper<>();
        worksDetailsEntityWrapper.where(worksDetails.getState()!=null," state ={0}",worksDetails.getState())
                .orderBy(orderBycolnum);
        return worksDetailsService.selectPage(pages,worksDetailsEntityWrapper);
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


}
