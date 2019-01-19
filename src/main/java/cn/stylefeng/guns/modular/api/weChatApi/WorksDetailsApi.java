package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.system.transfer.*;
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

import java.util.*;

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
    public Object list(@RequestParam(required=true,defaultValue="1") Integer page,@RequestParam(required=false,defaultValue="list") String type, @RequestParam(required=false,defaultValue="create_time") String orderBy) {
        List<String> orderList = new ArrayList<>();
        if(!StringUtils.isEmpty(orderBy) && "giveLike".toLowerCase().equals(orderBy.toLowerCase())){
            orderList.add("give_like_number");
        }else if(!StringUtils.isEmpty(orderBy) && "champion".toLowerCase().equals(orderBy.toLowerCase())){
            orderList.add("champion_reply");
            orderList.add("create_time");
        }else{
            orderList.add("create_time");
        }
        Integer pageSize = 12;
        if(!"list".equals(type)){
            pageSize = 5;
        }
        Page<WorksDetails> pages =  new Page<>(page,pageSize);
        WorksDetails worksDetails = new WorksDetails();
        worksDetails.setState(1);
        EntityWrapper<WorksDetails> worksDetailsEntityWrapper = new EntityWrapper<>();
        worksDetailsEntityWrapper.where(worksDetails.getState()!=null," state ={0} and details_delete=0",worksDetails.getState())
                .orderDesc(orderList);
        Page<WorksDetails> worksDetailsPage = worksDetailsService.selectPage(pages, worksDetailsEntityWrapper);
        Page<WorksDetailsBeanDto> resultPage = new Page<>();
        BeanUtils.copyProperties(worksDetailsPage,resultPage);
        List<WorksDetails> records = worksDetailsPage.getRecords();
        int size = records.size();
        List<WorksDetailsBeanDto> listWorksDetailsBeanDto = new ArrayList<>(size);
        WorksDetailsBeanDto beanDto = null;
        for(int i=0;i<size;i++){
            beanDto = new WorksDetailsBeanDto();
            WorksDetails worksDetails1 = records.get(i);
            BeanUtils.copyProperties(worksDetails1,beanDto);
            if(size>5 ){
                if(size%2==0 && i==4){
                    beanDto.setShowType(1);
                }else if(i<3){
                        beanDto.setShowType(3);
                }else{
                    beanDto.setShowType(2);
                }
            }else if(size==5){
                if(i<2){
                    beanDto.setShowType(2);
                }else {
                    beanDto.setShowType(3);
                }
            }else{
                if(size%2!=0){
                    beanDto.setShowType(size);
                }else {
                    beanDto.setShowType(2);
                }
            }
            listWorksDetailsBeanDto.add(beanDto);
        }
        resultPage.setRecords(listWorksDetailsBeanDto);
        return  new SuccessResponseData(resultPage);
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
        return  new SuccessResponseData(resultMap);
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
        Set<Long> userIdList = new HashSet<>();
        for(GiveLikeDetails details : giveLikeDetailss){
            userIdList.add(details.getUid());
        }
        Map<Long, User> mapUserInfoByUserIdList = getMapUserInfoByUserIdList(userIdList);
        List<GiveLikeDetailsDto> listDto = new ArrayList<>();
        GiveLikeDetailsDto dto;
        for(GiveLikeDetails details : giveLikeDetailss){
            dto = new GiveLikeDetailsDto();
            BeanUtils.copyProperties(details,dto);
            User user = mapUserInfoByUserIdList.get(dto.getUid());
            if(user!=null){
                dto.setUname(user.getName());
                dto.setPhoto(user.getAvatar());
            }
            listDto.add(dto);
        }
        return  new SuccessResponseData(listDto);
    }

    /**
     * 获取用户信息 
     * @param userIdList
     * @return
     */
    private Map<Long,User> getMapUserInfoByUserIdList(Set<Long> userIdList) {
        Map<Long,User> userInfoMap = new HashMap<>();
        if(userIdList==null || userIdList.size()==0){
            return userInfoMap;
        }
        List<User> users = userService.selectBatchIds(userIdList);
        for(User user : users){
            userInfoMap.put(Long.valueOf(user.getId()),user);
        }
        return userInfoMap;
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
        wrapper.where("model = {0} and business_id = {1} and reply_state = 1 ",replyDetails.getModel(),replyDetails.getBusinessId())
                .orderBy("create_time",false);
        List<ReplyDetails> replyDetailss = replyDetailsService.selectList(wrapper);
//        // 根据用户ID 获取用户信息
        // 根据用户ID 获取用户信息
        Set<Long> userIdList = new HashSet<>();
        for(ReplyDetails details : replyDetailss){
            userIdList.add(details.getUid());
        }
        Map<Long, User> mapUserInfoByUserIdList = getMapUserInfoByUserIdList(userIdList);
        List<ReplyDetailsDto> listDto = new ArrayList<>();
        ReplyDetailsDto dto;
        for(ReplyDetails details : replyDetailss){
            dto = new ReplyDetailsDto();
            BeanUtils.copyProperties(details,dto);
            User user = mapUserInfoByUserIdList.get(dto.getUid());
            if(user!=null){
                dto.setUname(user.getName());
                dto.setPhoto(user.getAvatar());
            }
            listDto.add(dto);
        }
        return  new SuccessResponseData(listDto);
    }


}
