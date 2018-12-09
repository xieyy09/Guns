package cn.stylefeng.guns.modular.worksDetail.service;

import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.system.transfer.WorksDetailsDto;
import com.baomidou.mybatisplus.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 作品 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
public interface IWorksDetailsService extends IService<WorksDetails> {

    boolean insertWorksDetailsDto(WorksDetailsDto worksDetailsDto);

    boolean updateWorksDetailsDto(WorksDetailsDto worksDetailsDto);

    boolean updateorwardNumber(Serializable serializable);

}
