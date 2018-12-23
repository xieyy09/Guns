package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
@TableName("give_like_details")
public class GiveLikeDetails extends Model<GiveLikeDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * 模块
     */
    private String model;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 业务ID
     */
    @TableField("business_id")
    private String businessId;
    /**
     * 点赞时间
     */
    @TableField("create_time")
    private Date createTime;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.model;
    }

    @Override
    public String toString() {
        return "GiveLikeDetails{" +
        ", model=" + model +
        ", uid=" + uid +
        ", businessId=" + businessId +
        ", createTime=" + createTime +
        "}";
    }
}
