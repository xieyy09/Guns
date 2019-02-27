package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dao.WorksDetailsMapper;
import cn.stylefeng.guns.modular.system.model.ChampionReply;
import cn.stylefeng.guns.modular.system.dao.ChampionReplyMapper;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.system.service.IChampionReplyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 擂主回复表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-02-27
 */
@Service
public class ChampionReplyServiceImpl extends ServiceImpl<ChampionReplyMapper, ChampionReply> implements IChampionReplyService {
    @Autowired
    ChampionReplyMapper championReplyMapper;
    @Autowired
    WorksDetailsMapper worksDetailsMapper;
    public boolean insertChampionReply(ChampionReply championReply){
        boolean flage = false;
        championReplyMapper.insert(championReply);
        if(championReply.getModel().equals("worksDetail")) {
            WorksDetails worksDetails = worksDetailsMapper.selectById(championReply.getBusinessId());
            worksDetails.setChampionReply(1);
            worksDetailsMapper.updateById(worksDetails);
        }
        flage = true;
        return flage;
    }

    public List<Map<String,Object>> queryListChampionReplyBybusinessId(String businessId){
        return championReplyMapper.queryListChampionReplyBybusinessId(businessId);
    }
}
