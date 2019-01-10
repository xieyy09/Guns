package cn.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author JasonX
 * @since 2019-01-09
 */
@TableName("headline_info")
public class HeadlineInfo extends Model<HeadlineInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 头条图片ID
     */
    private String id;
    /**
     * 头条标题
     */
    private String title;
    /**
     * 头条图片
     */
    private String img;
    /**
     * 图片描述
     */
    @TableField("img_content")
    private String imgContent;
    /**
     * 模块
     */
    private String module;
    /**
     * 对应对象ID
     */
    private String oid;
    /**
     * 链接url
     */
    private String url;
    /**
     * 是否发布(0否1是)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建人
     */
    private Integer createuser;


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

    public String getImgContent() {
        return imgContent;
    }

    public void setImgContent(String imgContent) {
        this.imgContent = imgContent;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreateuser() {
        return createuser;
    }

    public void setCreateuser(Integer createuser) {
        this.createuser = createuser;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HeadlineInfo{" +
        ", id=" + id +
        ", title=" + title +
        ", img=" + img +
        ", imgContent=" + imgContent +
        ", module=" + module +
        ", oid=" + oid +
        ", url=" + url +
        ", status=" + status +
        ", createtime=" + createtime +
        ", createuser=" + createuser +
        "}";
    }
}
