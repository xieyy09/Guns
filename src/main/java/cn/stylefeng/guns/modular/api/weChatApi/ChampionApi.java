package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.champion.service.IChampionService;
import cn.stylefeng.guns.modular.science.service.IPopularScienceBaseService;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.guns.modular.system.model.PopularScienceBase;
import cn.stylefeng.guns.modular.system.model.ReplyDetails;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.guns.modular.worksDetail.service.IGiveLikeDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IReplyDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksImgDetailsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.reflection.ArrayUtil;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 擂主管理
 */
@RestController
@RequestMapping("/weChatApi/champio")
@Slf4j
public class ChampionApi extends BaseController {
    @Autowired
    private IChampionService championService;
    @Autowired
    private IWorksDetailsService worksDetailsService;
    @Autowired
    private IReplyDetailsService replyDetailsService;
    @Autowired
    private IPopularScienceBaseService popularScienceBaseService;
    @GetMapping("/list")
    public Object findChampionList(@RequestParam(required=true,defaultValue="1") Integer page){
        try {
            Page<Champion> pages =  new Page<>(page,12);
            Wrapper<Champion> wrapper=new EntityWrapper<>();
            wrapper.orderDesc(Arrays.asList("create_time"));
            Page<Champion> champions = championService.selectPage(pages,wrapper);
            return  new SuccessResponseData(champions);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询擂主列表失败！");
        }
    }

    @GetMapping("/info")
    public Object findChampionInfo(@RequestParam("id") String id){
        try {
            Champion champion = championService.selectById(id);
            String popularIds = champion.getPopularIds();
            Map<String,Object> resultMap = new HashMap();
            List<PopularScienceBase> popularScienceBases = Collections.EMPTY_LIST;
            if(popularIds!=null && popularIds.length()>0){
                // 擂主和基地关系
                String[] listPopularId  = popularIds.split(",");
                List<String> listStrId = new ArrayList<>(listPopularId.length);
                for (String s : listPopularId) {
                    listStrId.add(s);
                }
               popularScienceBases = popularScienceBaseService.selectBatchIds(listStrId);
            }
            List<Map<String,Object>> worksDetailsList = new ArrayList<>();
            if(champion.getUid()!=null&&champion.getUid()>0){
                Wrapper<ReplyDetails> wrapper = new EntityWrapper();
                ReplyDetails replyDetails = new ReplyDetails();
                replyDetails.setModel(BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
                replyDetails.setUid(Long.valueOf(champion.getUid()));
                wrapper.where("model = {0} and uid = {1} and reply_state = 1 ",replyDetails.getModel(),replyDetails.getUid())
                        .orderBy("create_time",false);
                List<ReplyDetails> replyDetailss = replyDetailsService.selectList(wrapper);
                List<String> listDetailId = new ArrayList<>();
                for(ReplyDetails reply : replyDetailss){
                    listDetailId.add(reply.getId());
                }
                if(listDetailId.size()>0){
                    Wrapper<WorksDetails> worksDetailsWarp = new EntityWrapper();
                    WorksDetails worksDetails = new WorksDetails();
                    worksDetailsWarp.where("details_delete=0 and state=1 ").in("id",listDetailId).orderBy("create_time");
                    List<WorksDetails> worksDetailss = worksDetailsService.selectList(worksDetailsWarp);
                    Map<String,Object> mapInfo = null;
                    for(WorksDetails details : worksDetailss){
                        mapInfo = new HashedMap();
                        mapInfo.put("id",details.getId());
                        mapInfo.put("imgUrl",details.getImgUrl());
                        mapInfo.put("imgRemark",details.getImgRemark());
                        mapInfo.put("createTime",details.getCreateTime());
                        worksDetailsList.add(mapInfo);
                    }
                }
            }
            resultMap.put("champion",champion);
            resultMap.put("popularScienceBases",popularScienceBases);
            resultMap.put("worksDetailsList",worksDetailsList);
            return new SuccessResponseData(resultMap);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询擂主详情失败！");
        }
    }
}
