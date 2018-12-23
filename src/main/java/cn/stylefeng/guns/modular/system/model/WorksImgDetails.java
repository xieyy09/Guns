package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 作品图片
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
@TableName("works_img_details")
public class WorksImgDetails extends Model<WorksImgDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 作品ID
     */
    @TableField("works_details_id")
    private String worksDetailsId;
    /**
     * 作品顺序
     */
    @TableField("detail_index")
    private Integer detailIndex;
    /**
     * 图片地址
     */
    @TableField("detail_img")
    private String detailImg;
    /**
     * 说明
     */
    private String remark;
    /**
     * 位置
     */
    @TableField("img_point")
    private String imgPoint;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorksDetailsId() {
        return worksDetailsId;
    }

    public void setWorksDetailsId(String worksDetailsId) {
        this.worksDetailsId = worksDetailsId;
    }

    public Integer getDetailIndex() {
        return detailIndex;
    }

    public void setDetailIndex(Integer detailIndex) {
        this.detailIndex = detailIndex;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgPoint() {
        return imgPoint;
    }

    public void setImgPoint(String imgPoint) {
        this.imgPoint = imgPoint;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WorksImgDetails{" +
        ", id=" + id +
        ", worksDetailsId=" + worksDetailsId +
        ", detailIndex=" + detailIndex +
        ", detailImg=" + detailImg +
        ", remark=" + remark +
        ", imgPoint=" + imgPoint +
        "}";
    }
}
