package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.modular.champion.service.IChampionService;
import cn.stylefeng.guns.modular.science.service.IPopularScienceBaseService;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.guns.modular.system.model.PopularScienceBase;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 科普基地API
 */
@RestController
@RequestMapping("/weChatApi/science")
@Slf4j
public class ScienceApi extends BaseController {
    @Autowired
    private IPopularScienceBaseService popularScienceBaseService;
    @Autowired
    private IChampionService championService;

    @GetMapping("/list")
    public Object findPopularScienceList(@RequestParam(value = "limit",defaultValue = "5") String limit){
        try {
            Wrapper<PopularScienceBase> wrapper = new EntityWrapper<>();
            wrapper.orderDesc(Arrays.asList("ind"));
            List<PopularScienceBase> list = popularScienceBaseService.selectList(wrapper);
            return list;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询科普基地列表失败！");
        }
    }

    @GetMapping("/info")
    public Object findPopularScienceInfo(@RequestParam("id") String id){
        try {
            PopularScienceBase popularScienceBase = popularScienceBaseService.selectById(id);
            // 获取基地和擂主关联关系
            String id1 = popularScienceBase.getId();
            Wrapper<Champion> wrapper = new EntityWrapper<>();
            wrapper.where("state=1").like("popular_ids",id1);
            List<Champion> champions = championService.selectList(wrapper);

            return popularScienceBase;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询科普基地详情失败！");
        }
    }
}
