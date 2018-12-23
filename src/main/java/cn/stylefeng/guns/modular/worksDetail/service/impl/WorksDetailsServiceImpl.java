package cn.stylefeng.guns.modular.worksDetail.service.impl;

import cn.stylefeng.guns.config.util.UUIDUtils;
import cn.stylefeng.guns.core.util.BUSINESS_MODE_ENUM;
import cn.stylefeng.guns.modular.system.dao.GiveLikeDetailsMapper;
import cn.stylefeng.guns.modular.system.dao.ReplyDetailsMapper;
import cn.stylefeng.guns.modular.system.dao.WorksImgDetailsMapper;
import cn.stylefeng.guns.modular.system.model.GiveLikeDetails;
import cn.stylefeng.guns.modular.system.model.ReplyDetails;
import cn.stylefeng.guns.modular.system.model.WorksDetails;
import cn.stylefeng.guns.modular.system.dao.WorksDetailsMapper;
import cn.stylefeng.guns.modular.system.model.WorksImgDetails;
import cn.stylefeng.guns.modular.system.transfer.WorksDetailsDto;
import cn.stylefeng.guns.modular.system.transfer.WorksImgDetailsDto;
import cn.stylefeng.guns.modular.worksDetail.service.IWorksDetailsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 作品 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
@Service
public class WorksDetailsServiceImpl extends ServiceImpl<WorksDetailsMapper, WorksDetails> implements IWorksDetailsService {
    @Autowired
    private WorksImgDetailsMapper worksImgDetailsMapper;
    @Autowired
    private WorksDetailsMapper worksDetailsMapper;
    @Autowired
    private GiveLikeDetailsMapper giveLikeDetailsMapper;
    @Autowired
    private ReplyDetailsMapper replyDetailsMapper;

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean insertWorksDetailsDto(WorksDetailsDto worksDetailsDto) {
        boolean flage = Boolean.FALSE;
        String worksDetailsId = UUIDUtils.getBase64UUID();
        // 获取作品图片信息 图片信息备注第一个
        String firstImgUrl = null;
        String firstImgRemark = null;
        int imgnum = 0;
        for(WorksImgDetailsDto imgDetailsDto : worksDetailsDto.getWorksImgDetailsList()){
            if(imgDetailsDto.getDetailImg()!=null){
                if(firstImgUrl==null){
                    firstImgUrl = imgDetailsDto.getDetailImg();
                }
                if(firstImgRemark==null){
                    firstImgRemark = imgDetailsDto.getRemark();
                }
                imgnum++;
                WorksImgDetails imgDetails = new WorksImgDetails();
                BeanUtils.copyProperties(imgDetailsDto,imgDetails);
                imgDetails.setWorksDetailsId(worksDetailsId);
                imgDetails.setId(UUIDUtils.getBase64UUID());
                imgDetails.setDetailIndex(imgnum);
                worksImgDetailsMapper.insert(imgDetails);
            }
        }
        WorksDetails workDetails = new WorksDetails();
        BeanUtils.copyProperties(worksDetailsDto,workDetails);
        workDetails.setImgNumber(imgnum);
        workDetails.setImgUrl(firstImgUrl);
        workDetails.setImgRemark(firstImgRemark);
        workDetails.setCreateTime(new Date());
        workDetails.setForwardNumber(0);
        workDetails.setGiveLikeNumber(0);
        workDetails.setReplyNumber(0);
        workDetails.setState(0);
        workDetails.setId(worksDetailsId);
        worksDetailsMapper.insert(workDetails);
        flage = Boolean.TRUE;
        return flage;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateWorksDetailsDto(WorksDetailsDto worksDetailsDto) {
        boolean flage = Boolean.FALSE;
        String worksDetailsId = worksDetailsDto.getId();
        // 删除作品图片
        WorksImgDetails worksImgDetails = new WorksImgDetails();
        worksImgDetails.setWorksDetailsId(worksDetailsId);
        EntityWrapper<WorksImgDetails> worksImgDetailsEntityWrapper = new EntityWrapper<>();
        worksImgDetailsEntityWrapper.setEntity(worksImgDetails);
        worksImgDetailsMapper.delete(worksImgDetailsEntityWrapper);

        // 获取作品图片信息 图片信息备注第一个
        String firstImgUrl = null;
        String firstImgRemark = null;
        int imgnum = 0;
        for(WorksImgDetailsDto imgDetailsDto : worksDetailsDto.getWorksImgDetailsList()){
            if(imgDetailsDto.getDetailImg()!=null){
                if(firstImgUrl==null){
                    firstImgUrl = imgDetailsDto.getDetailImg();
                }
                if(firstImgRemark==null){
                    firstImgRemark = imgDetailsDto.getRemark();
                }
                imgnum++;
                WorksImgDetails imgDetails = new WorksImgDetails();
                BeanUtils.copyProperties(imgDetailsDto,imgDetails);
                imgDetails.setWorksDetailsId(worksDetailsId);
                imgDetails.setId(UUIDUtils.getBase64UUID());
                imgDetails.setDetailIndex(imgnum);
                worksImgDetailsMapper.insert(imgDetails);
            }
        }
        WorksDetails workDetails = new WorksDetails();
        BeanUtils.copyProperties(worksDetailsDto,workDetails);
        workDetails.setImgNumber(imgnum);
        workDetails.setImgUrl(firstImgUrl);
        workDetails.setImgRemark(firstImgRemark);
        workDetails.setState(0);
        worksDetailsMapper.updateById(workDetails);
        flage = Boolean.TRUE;
        return flage;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean deleteById(Serializable id) {
        boolean flage = Boolean.FALSE;
        // 删除作品图片
        WorksImgDetails worksImgDetails = new WorksImgDetails();
        worksImgDetails.setWorksDetailsId(id.toString());
        EntityWrapper<WorksImgDetails> worksImgDetailsEntityWrapper = new EntityWrapper<>();
        worksImgDetailsEntityWrapper.setEntity(worksImgDetails);
        worksImgDetailsMapper.delete(worksImgDetailsEntityWrapper);
        // 删除作品点赞记录
        EntityWrapper<GiveLikeDetails> giveLikeDetailsEntityWrapper = new EntityWrapper<>();
        GiveLikeDetails giveLikeDetails = new GiveLikeDetails();
        giveLikeDetails.setBusinessId(id.toString());
        giveLikeDetails.setModel(BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
        giveLikeDetailsEntityWrapper.setEntity(giveLikeDetails);
        giveLikeDetailsMapper.delete(giveLikeDetailsEntityWrapper);
        // 删除作品提问记录
        Wrapper<ReplyDetails> replyDetailsEntityWrapper = new EntityWrapper<>();
        replyDetailsEntityWrapper.where("business_id={0} and model={1}",id.toString(),BUSINESS_MODE_ENUM.WORKS_DETAILS.name());
        replyDetailsMapper.delete(replyDetailsEntityWrapper);
        // 删除作品信息
        worksDetailsMapper.deleteById(id);
        flage = Boolean.TRUE;
        return flage;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateorwardNumber(Serializable serializable){
        boolean flage = Boolean.FALSE;
        WorksDetails worksDetails = worksDetailsMapper.selectById(serializable);
        Integer forwardNumber = worksDetails.getForwardNumber();
        forwardNumber++;
        worksDetails.setForwardNumber(forwardNumber);
        worksDetailsMapper.updateById(worksDetails);
        flage = Boolean.TRUE;
        return flage;
    }


}
