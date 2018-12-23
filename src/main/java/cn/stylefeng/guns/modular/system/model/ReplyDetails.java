package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-23
 */
@TableName("reply_details")
public class ReplyDetails extends Model<ReplyDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 业务ID
     */
    @TableField("business_id")
    private String businessId;
    /**
     * 模块
     */
    private String model;
    /**
     * 父评论ID
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 评论人id
     */
    private Long uid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 状态(0待审核1审核通过 -1 不通过)
     */
    @TableField("reply_state")
    private Integer replyState;
    /**
     * 点赞数量
     */
    @TableField("give_like_number")
    private Integer giveLikeNumber;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReplyState() {
        return replyState;
    }

    public void setReplyState(Integer replyState) {
        this.replyState = replyState;
    }

    public Integer getGiveLikeNumber() {
        return giveLikeNumber;
    }

    public void setGiveLikeNumber(Integer giveLikeNumber) {
        this.giveLikeNumber = giveLikeNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReplyDetails{" +
        ", id=" + id +
        ", businessId=" + businessId +
        ", model=" + model +
        ", parentId=" + parentId +
        ", uid=" + uid +
        ", content=" + content +
        ", createTime=" + createTime +
        ", replyState=" + replyState +
        ", giveLikeNumber=" + giveLikeNumber +
        "}";
    }
}
