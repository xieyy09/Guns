package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 擂主回复表
 * </p>
 *
 * @author admin
 * @since 2019-02-27
 */
@TableName("champion_reply")
public class ChampionReply extends Model<ChampionReply> {

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
     * 评论人id
     */
    @TableField("champion_id")
    private String championId;
    /**
     * 评论内容
     */
    private String content;
    private String businessTitle;
    private String championName;
    /**
     * 评论时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 状态(0不删除 1删除)
     */
    @TableField("reply_delete")
    private Integer replyDelete;


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

    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
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

    public Integer getReplyDelete() {
        return replyDelete;
    }

    public void setReplyDelete(Integer replyDelete) {
        this.replyDelete = replyDelete;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ChampionReply{" +
        ", id=" + id +
        ", businessId=" + businessId +
        ", model=" + model +
        ", championId=" + championId +
        ", content=" + content +
        ", createTime=" + createTime +
        ", replyDelete=" + replyDelete +
        "}";
    }
}
