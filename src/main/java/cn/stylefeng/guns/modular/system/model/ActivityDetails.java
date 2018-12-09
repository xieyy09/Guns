package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 活动
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-08
 */
@TableName("activity_details")
public class ActivityDetails extends Model<ActivityDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 标题
     */
    @NotNull
    private String title;
    /**
     * 图片
     */
    private String img;
    /**
     * 内容
     */
    @NotNull
    private String content;
    /**
     * 开始时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("begin_time")
    private Date beginTime;
    /**
     * 结束时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("end_time")
    private Date endTime;
    /**
     * 状态(-1已结束0未开始1进行中)
     */
    @TableField("activity_state")
    private Integer activityState;
    /**
     * 发布人ID
     */
    private Long uid;
    /**
     * 发布时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 参加人数
     */
    @TableField("user_number")
    private Integer userNumber;
    /**
     * 发布状态(0未发布 已发布)
     */
    private Integer state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getActivityState() {
        return activityState;
    }

    public void setActivityState(Integer activityState) {
        this.activityState = activityState;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ActivityDetails{" +
        ", id=" + id +
        ", title=" + title +
        ", img=" + img +
        ", content=" + content +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", activityState=" + activityState +
        ", uid=" + uid +
        ", createTime=" + createTime +
        ", userNumber=" + userNumber +
        ", state=" + state +
        "}";
    }
}
