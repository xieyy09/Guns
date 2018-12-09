package cn.stylefeng.guns.modular.worksDetail.service.impl;

import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.dao.WorksDetailsMapper;
import cn.stylefeng.guns.modular.system.model.ReplyDetails;
import cn.stylefeng.guns.modular.system.dao.ReplyDetailsMapper;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IReplyDetailsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateById(ReplyDetails replyDetails) {
       boolean flage = Boolean.FALSE;
       // 更新作品的回复数量
        if(replyDetails.getModel().equals(BUSINESS_MODE_ENUM.WORKS_DETAILS.name()) && 1==replyDetails.getReplyState()){
            WorksDetails worksDetails = worksDetailsMapper.selectById(replyDetails.getBusinessId());
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
