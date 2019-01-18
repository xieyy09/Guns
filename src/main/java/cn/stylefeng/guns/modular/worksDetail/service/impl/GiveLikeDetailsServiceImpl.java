package cn.stylefeng.guns.modular.worksDetail.service.impl;

import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.dao.WorksDetailsMapper;
import cn.stylefeng.guns.modular.system.model.GiveLikeDetails;
import cn.stylefeng.guns.modular.system.dao.GiveLikeDetailsMapper;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.worksDetail.service.IGiveLikeDetailsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
@Service
public class GiveLikeDetailsServiceImpl extends ServiceImpl<GiveLikeDetailsMapper, GiveLikeDetails> implements IGiveLikeDetailsService {
    @Autowired
    WorksDetailsMapper worksDetailsMapper;
    @Autowired
    GiveLikeDetailsMapper giveLikeDetailsMapper;

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean insert(GiveLikeDetails giveLikeDetails){
        boolean flage = Boolean.FALSE;
        String model = giveLikeDetails.getModel();
        if(BUSINESS_MODE_ENUM.WORKS_DETAILS.name().equals(model)) {
            WorksDetails worksDetails = worksDetailsMapper.selectById(giveLikeDetails.getBusinessId());
            int giveLikeNumber = worksDetails.getGiveLikeNumber()+1;
            worksDetails.setGiveLikeNumber(giveLikeNumber);
            worksDetailsMapper.updateById(worksDetails);
        }
        giveLikeDetailsMapper.insert(giveLikeDetails);
        flage = Boolean.TRUE;
        return flage;
    }

}
