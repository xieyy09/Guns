package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.ChampionReply;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 擂主回复表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-02-27
 */
public interface IChampionReplyService extends IService<ChampionReply> {
    boolean insertChampionReply(ChampionReply championReply);
    List<Map<String,Object>> queryListChampionReplyBybusinessId(String businessId);
}
