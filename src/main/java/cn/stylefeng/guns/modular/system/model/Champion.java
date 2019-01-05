package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 擂主
 * </p>
 *
 * @author JasonX
 * @since 2018-12-10
 */
@TableName("champion")
public class Champion extends Model<Champion> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 擂主姓名
     */
    @TableField("champion_name")
    private String championName;
    /**
     * 系统帐户ID
     */
    private Long uid;
    /**
     * 擂主说明
     */
    @TableField("champion_remark")
    private String championRemark;
    /**
     * 专业领域
     */
    @TableField("professional_field")
    private String professionalField;
    @TableField("phone")
    private String phone;
    /**
     * 签名
     */
    private String sign;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人ID
     */
    @TableField("create_uid")
    private Long createUid;
    /**
     * 评论数量
     */
    @TableField("reply_number")
    private Integer replyNumber;
    /**
     * 状态(0未发布1亿发布)
     */
    private Integer state;

    private String img;
    /**
     * 擂主对应的基地
     */
    @TableField("popular_ids")
    private String popularIds;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getChampionRemark() {
        return championRemark;
    }

    public void setChampionRemark(String championRemark) {
        this.championRemark = championRemark;
    }

    public String getProfessionalField() {
        return professionalField;
    }

    public void setProfessionalField(String professionalField) {
        this.professionalField = professionalField;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Integer getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(Integer replyNumber) {
        this.replyNumber = replyNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPopularIds() {
        return popularIds;
    }

    public void setPopularIds(String popularIds) {
        this.popularIds = popularIds;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Champion{" +
        ", id=" + id +
        ", championName=" + championName +
        ", uid=" + uid +
        ", championRemark=" + championRemark +
        ", professionalField=" + professionalField +
        ", sign=" + sign +
        ", createTime=" + createTime +
        ", createUid=" + createUid +
        ", replyNumber=" + replyNumber +
        ", state=" + state +
        "}";
    }
}
