package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.WorksDetails;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 * 作品 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
public interface WorksDetailsMapper extends BaseMapper<WorksDetails> {
    WorksDetails selectById(Serializable var1);
    Integer updateById(WorksDetails worksDetails);
}
