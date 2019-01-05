package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 作品
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
@TableName("works_details")
public class WorksDetails extends Model<WorksDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 活动ID
     */
    @TableId("activity_id")
    private Long activityId;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 评论数量
     */
    @TableField("reply_number")
    private Integer replyNumber;
    /**
     * 转发数量
     */
    @TableField("forward_number")
    private Integer forwardNumber;
    /**
     * 点赞数量
     */
    @TableField("give_like_number")
    private Integer giveLikeNumber;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 审核状态(0待审核1通过-1不通过)
     */
    private Integer state;
    /**
     * 是否定位(0无定位1有定位)
     */
    @TableField("has_point")
    private Integer hasPoint;
    /**
     * 作品名称
     */
    @TableField("works_title")
    private String worksTitle;
    /**
     * 图片数量
     */
    @TableField("img_number")
    private Integer imgNumber;
    /**
     * 第一个图片地址
     */
    @TableField("img_url")
    private String imgUrl;
    /**
     * 图片说明
     */
    @TableField("img_remark")
    private String imgRemark;
    /**
     * 拍摄时间
     */
    @TableField("pohto_time")
    private Date pohtoTime;
    /**
     * 天气
     */
    private String weather;
    /**
     * 拍摄地点
     */
    private String address;
    /**
     * 拍摄作者
     */
    @TableField("taken_author")
    private String takenAuthor;
    /**
     * 拍摄工具
     */
    @TableField("taken_tool")
    private String takenTool;
    /**
     * 二记
     */
    private String content;
    /**
     * 问题1
     */
    @TableField("answer_one")
    private String answerOne;
    /**
     * 问题2
     */
    @TableField("answer_two")
    private String answerTwo;
    /**
     * 问题3
     */
    @TableField("answer_three")
    private String answerThree;
    /**
     * 问题4
     */
    @TableField("answer_four")
    private String answerFour;
    /**
     * 问题5
     */
    @TableField("answer_five")
    private String answerFive;
    /**
     * 问题6
     */
    @TableField("answer_six")
    private String answerSix;

    /**
     * 是否擂主回复
     */
    @TableField("champion_reply")
    private Integer championReply;

    @TableField("details_delete")
    private Integer detailsDelete;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(Integer replyNumber) {
        this.replyNumber = replyNumber;
    }

    public Integer getForwardNumber() {
        return forwardNumber;
    }

    public void setForwardNumber(Integer forwardNumber) {
        this.forwardNumber = forwardNumber;
    }

    public Integer getGiveLikeNumber() {
        return giveLikeNumber;
    }

    public void setGiveLikeNumber(Integer giveLikeNumber) {
        this.giveLikeNumber = giveLikeNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getHasPoint() {
        return hasPoint;
    }

    public void setHasPoint(Integer hasPoint) {
        this.hasPoint = hasPoint;
    }

    public String getWorksTitle() {
        return worksTitle;
    }

    public void setWorksTitle(String worksTitle) {
        this.worksTitle = worksTitle;
    }

    public Integer getImgNumber() {
        return imgNumber;
    }

    public void setImgNumber(Integer imgNumber) {
        this.imgNumber = imgNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgRemark() {
        return imgRemark;
    }

    public void setImgRemark(String imgRemark) {
        this.imgRemark = imgRemark;
    }

    public Date getPohtoTime() {
        return pohtoTime;
    }

    public void setPohtoTime(Date pohtoTime) {
        this.pohtoTime = pohtoTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTakenAuthor() {
        return takenAuthor;
    }

    public void setTakenAuthor(String takenAuthor) {
        this.takenAuthor = takenAuthor;
    }

    public String getTakenTool() {
        return takenTool;
    }

    public void setTakenTool(String takenTool) {
        this.takenTool = takenTool;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }

    public String getAnswerFive() {
        return answerFive;
    }

    public void setAnswerFive(String answerFive) {
        this.answerFive = answerFive;
    }

    public String getAnswerSix() {
        return answerSix;
    }

    public void setAnswerSix(String answerSix) {
        this.answerSix = answerSix;
    }

    public Integer getChampionReply() {
        return championReply;
    }

    public void setChampionReply(Integer championReply) {
        this.championReply = championReply;
    }

    public Integer getDetailsDelete() {
        return detailsDelete;
    }

    public void setDetailsDelete(Integer detailsDelete) {
        this.detailsDelete = detailsDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.activityId;
    }

    @Override
    public String toString() {
        return "WorksDetails{" +
        ", id=" + id +
        ", activityId=" + activityId +
        ", uid=" + uid +
        ", replyNumber=" + replyNumber +
        ", forwardNumber=" + forwardNumber +
        ", giveLikeNumber=" + giveLikeNumber +
        ", createTime=" + createTime +
        ", state=" + state +
        ", hasPoint=" + hasPoint +
        ", worksTitle=" + worksTitle +
        ", imgNumber=" + imgNumber +
        ", imgUrl=" + imgUrl +
        ", imgRemark=" + imgRemark +
        ", pohtoTime=" + pohtoTime +
        ", weather=" + weather +
        ", address=" + address +
        ", takenAuthor=" + takenAuthor +
        ", takenTool=" + takenTool +
        ", content=" + content +
        ", answerOne=" + answerOne +
        ", answerTwo=" + answerTwo +
        ", answerThree=" + answerThree +
        ", answerFour=" + answerFour +
        ", answerFive=" + answerFive +
        ", answerSix=" + answerSix +
        "}";
    }
}
