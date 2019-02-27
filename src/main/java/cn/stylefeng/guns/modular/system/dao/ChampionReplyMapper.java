package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.ChampionReply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 擂主回复表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-02-27
 */
public interface ChampionReplyMapper extends BaseMapper<ChampionReply> {
    List<Map<String,Object>> queryListChampionReplyBybusinessId(String businessId);
}
