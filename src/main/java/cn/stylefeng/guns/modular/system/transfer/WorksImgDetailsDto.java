package cn.stylefeng.guns.modular.system.transfer;

import lombok.Data;

import java.io.Serializable;

/**
 * 作品图片信息
 * Created by lyc on 2018/12/22.
 */
@Data
public class WorksImgDetailsDto{

    /**
     * id
     */
    String id;
    /**
     * 作品ID
     */
    String worksDetailsId;
    /**
     * 作品顺序
     */
    Integer detailIndex;
    /**
     * 图片地址
     */
    String detailImg;
    /**
     * 说明
     */
    String remark;
    /**
     * 位置
     */
    String imgPoint;

}
