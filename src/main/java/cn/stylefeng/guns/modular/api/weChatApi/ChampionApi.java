package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.modular.champion.service.IChampionService;
import cn.stylefeng.guns.modular.science.service.IPopularScienceBaseService;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.guns.modular.system.model.PopularScienceBase;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
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
    private IPopularScienceBaseService popularScienceBaseService;
    @GetMapping("/list")
    public Object findChampionList(@RequestParam(required=true,defaultValue="1") Integer page){
        try {
            Page<Champion> pages =  new Page<>(page,12);
            Wrapper<Champion> wrapper=new EntityWrapper<>();
            wrapper.orderDesc(Arrays.asList("create_time"));
            Page<Champion> champions = championService.selectPage(pages,wrapper);
            SuccessResponseData responseData = new SuccessResponseData();
            responseData.setData(champions);
            responseData.setCode(ResponseData.DEFAULT_SUCCESS_CODE);
            responseData.setMessage(ResponseData.DEFAULT_SUCCESS_MESSAGE);
            return responseData;
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
            resultMap.put("champion",champion);
            resultMap.put("popularScienceBases",popularScienceBases);
            return new SuccessResponseData(resultMap);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询擂主详情失败！");
        }
    }
}
