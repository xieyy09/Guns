package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 科普基地
 * </p>
 *
 * @author JasonX
 * @since 2018-12-10
 */
@TableName("popular_science_base")
public class PopularScienceBase extends Model<PopularScienceBase> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 科普基地名称
     */
    private String title;
    /**
     * 科普基地图片
     */
    private String img;
    /**
     * 科普基地说明
     */
    private String remark;
    /**
     * 科普基地内容
     */
    private String content;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人ID
     */
    private Long uid;
    /**
     * 顺序
     */
    private Integer ind;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getInd() {
        return ind;
    }

    public void setInd(Integer ind) {
        this.ind = ind;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PopularScienceBase{" +
        ", id=" + id +
        ", title=" + title +
        ", img=" + img +
        ", remark=" + remark +
        ", content=" + content +
        ", createTime=" + createTime +
        ", uid=" + uid +
        ", ind=" + ind +
        "}";
    }
}
