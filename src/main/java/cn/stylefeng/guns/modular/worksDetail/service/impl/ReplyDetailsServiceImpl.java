package cn.stylefeng.guns.modular.worksDetail.service.impl;

import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.dao.ChampionMapper;
import cn.stylefeng.guns.modular.system.dao.WorksDetailsMapper;
import cn.stylefeng.guns.modular.system.model.Champion;
import cn.stylefeng.guns.modular.system.model.ReplyDetails;
import cn.stylefeng.guns.modular.system.dao.ReplyDetailsMapper;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IReplyDetailsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-23
 */
@Service
public class ReplyDetailsServiceImpl extends ServiceImpl<ReplyDetailsMapper, ReplyDetails> implements IReplyDetailsService {

    @Autowired
    ReplyDetailsMapper replyDetailsMapper;
    @Autowired
    WorksDetailsMapper worksDetailsMapper;
    @Autowired
    ChampionMapper championMapper;

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean insert(ReplyDetails entity) {
        String model = entity.getModel();
        if(BUSINESS_MODE_ENUM.WORKS_DETAILS.name().equals(model)){
            // 判断当前回复人是否擂主
            Long uid = entity.getUid();
            Wrapper<Champion> championWrapper = new EntityWrapper<>();
            championWrapper.where("uid={0} and state=1",uid);
            List<Champion> champions = championMapper.selectList(championWrapper);
            if(champions!=null && champions.size()>0){
                entity.setChampionReply(1);
                WorksDetails worksDetails = worksDetailsMapper.selectById(entity.getBusinessId());
                if(worksDetails!=null) {
                    worksDetails.setChampionReply(1);
                    worksDetailsMapper.updateById(worksDetails);
                }
            }
        }
        return retBool(this.baseMapper.insert(entity));
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateById(ReplyDetails replyDetails) {
       boolean flage = Boolean.FALSE;
        ReplyDetails replyDetailsTemp = replyDetailsMapper.selectById(replyDetails.getId());
        // 更新作品的回复数量
        if(replyDetailsTemp.getModel().equals(BUSINESS_MODE_ENUM.WORKS_DETAILS.name()) && 1==replyDetails.getReplyState()){
            WorksDetails worksDetails = worksDetailsMapper.selectById(replyDetailsTemp.getBusinessId());
            Integer replyNumber = worksDetails.getReplyNumber();
            replyNumber++;
            worksDetails.setReplyNumber(replyNumber);
            worksDetailsMapper.updateById(worksDetails);
        }
        replyDetailsMapper.updateById(replyDetails);
       flage = Boolean.TRUE;
       return flage;
    }


}
