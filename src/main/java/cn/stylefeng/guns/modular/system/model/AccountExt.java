package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 帐户扩展信息
 * </p>
 *
 * @author JasonX
 * @since 2018-12-24
 */
@TableName("account_ext")
public class AccountExt extends Model<AccountExt> {

    private static final long serialVersionUID = 1L;

    /**
     * uid
     */
    private Integer uid;
    /**
     * 禁止回帖(0允许1禁止)
     */
    @TableField("ban_reply")
    private Integer banReply;
    /**
     * 禁止发帖(0允许1禁止)
     */
    @TableField("ban_post")
    private Integer banPost;
    /**
     * 禁止点赞(0允许1禁止)
     */
    @TableField("ban_give_like")
    private Integer banGiveLike;
    /**
     * 最后一次发言时间
     */
    @TableField("last_speak_time")
    private Date lastSpeakTime;
    /**
     * 微信OPenID
     */
    @TableField("webchat_open_id")
    private String webchatOpenId;
    /**
     * 微信头像地址(系统默认给地址)
     */
    @TableField("webchat_pohto_url")
    private String webchatPohtoUrl;
    /**
     * 微信昵称
     */
    @TableField("webchat_name")
    private String webchatName;
    /**
     * 当天可点赞次数
     */
    @TableField("now_give_number")
    private Integer nowGiveNumber;
    @TableField("isEdit")
    private Integer isEdit;

    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBanReply() {
        return banReply;
    }

    public void setBanReply(Integer banReply) {
        this.banReply = banReply;
    }

    public Integer getBanPost() {
        return banPost;
    }

    public void setBanPost(Integer banPost) {
        this.banPost = banPost;
    }

    public Integer getBanGiveLike() {
        return banGiveLike;
    }

    public void setBanGiveLike(Integer banGiveLike) {
        this.banGiveLike = banGiveLike;
    }

    public Date getLastSpeakTime() {
        return lastSpeakTime;
    }

    public void setLastSpeakTime(Date lastSpeakTime) {
        this.lastSpeakTime = lastSpeakTime;
    }

    public String getWebchatOpenId() {
        return webchatOpenId;
    }

    public void setWebchatOpenId(String webchatOpenId) {
        this.webchatOpenId = webchatOpenId;
    }

    public String getWebchatPohtoUrl() {
        return webchatPohtoUrl;
    }

    public void setWebchatPohtoUrl(String webchatPohtoUrl) {
        this.webchatPohtoUrl = webchatPohtoUrl;
    }

    public String getWebchatName() {
        return webchatName;
    }

    public void setWebchatName(String webchatName) {
        this.webchatName = webchatName;
    }

    public Integer getNowGiveNumber() {
        return nowGiveNumber;
    }

    public void setNowGiveNumber(Integer nowGiveNumber) {
        this.nowGiveNumber = nowGiveNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

    @Override
    public String toString() {
        return "AccountExt{" +
        ", uid=" + uid +
        ", banReply=" + banReply +
        ", banPost=" + banPost +
        ", banGiveLike=" + banGiveLike +
        ", lastSpeakTime=" + lastSpeakTime +
        ", webchatOpenId=" + webchatOpenId +
        ", webchatPohtoUrl=" + webchatPohtoUrl +
        ", webchatName=" + webchatName +
        ", nowGiveNumber=" + nowGiveNumber +
        "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountExt that = (AccountExt) o;
        return Objects.equals(uid, that.uid) &&
                Objects.equals(banReply, that.banReply) &&
                Objects.equals(banPost, that.banPost) &&
                Objects.equals(banGiveLike, that.banGiveLike) &&
                Objects.equals(lastSpeakTime, that.lastSpeakTime) &&
                Objects.equals(webchatOpenId, that.webchatOpenId) &&
                Objects.equals(webchatPohtoUrl, that.webchatPohtoUrl) &&
                Objects.equals(webchatName, that.webchatName) &&
                Objects.equals(nowGiveNumber, that.nowGiveNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, banReply, banPost, banGiveLike, lastSpeakTime, webchatOpenId, webchatPohtoUrl, webchatName, nowGiveNumber);
    }
}
