package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.guns.modular.system.model.WikipediaHall;
import cn.stylefeng.guns.modular.wikipedia.service.IWikipediaHallService;
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
 * 百科讲堂API
 */
@RestController
@RequestMapping("/weChatApi/wikipedia")
@Slf4j
public class WikipediaApi extends BaseController {
    @Autowired
    private IWikipediaHallService wikipediaHallService;

    @GetMapping("/list")
    public Object findChampionList(@RequestParam(value = "limit",defaultValue = "5") String limit){
        try {
            Wrapper<WikipediaHall> wrapper=new EntityWrapper<>();
            wrapper.orderDesc(Arrays.asList("ind"));
            List<WikipediaHall> wikipediaHalls = wikipediaHallService.selectList(wrapper);
            return wikipediaHalls;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询百科讲堂列表失败！");
        }
    }

    @GetMapping("/info")
    public Object findChampionInfo(@RequestParam("id") String id){
        try {
            WikipediaHall wikipediaHall = wikipediaHallService.selectById(id);
            return wikipediaHall;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ErrorResponseData(500, "查询百科讲堂详情失败！");
        }
    }
}
