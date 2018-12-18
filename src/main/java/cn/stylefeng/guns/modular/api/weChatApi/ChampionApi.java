package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.modular.champion.service.IChampionService;
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
 * 擂主管理
 */
@RestController
@RequestMapping("/weChatApi/champio")
@Slf4j
public class ChampionApi extends BaseController {
    @Autowired
    private IChampionService championService;
    @GetMapping("/list")
    public Object findChampionList(@RequestParam(value = "limit",defaultValue = "5") String limit){
        try {
            Wrapper<Champion> wrapper=new EntityWrapper<>();
//            wrapper.orderDesc(Arrays.asList("ind"));
            List<Champion> champions = championService.selectList(wrapper);
            return champions;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询擂主列表失败！");
        }
    }

    @GetMapping("/info")
    public Object findChampionInfo(@RequestParam("id") String id){
        try {
            Champion champion = championService.selectById(id);
            return champion;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询擂主详情失败！");
        }
    }
}
