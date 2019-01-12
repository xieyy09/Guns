package cn.stylefeng.guns.modular.system.transfer;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 作品
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-22
 */
@Data
public class WorksDetailsBeanDto {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 活动ID
     */
    private String activityId;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 评论数量
     */
    private Integer replyNumber;
    /**
     * 转发数量
     */
    private Integer forwardNumber;
    /**
     * 点赞数量
     */
    private Integer giveLikeNumber;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审核状态(0待审核1通过-1不通过)
     */
    private Integer state;
    /**
     * 是否定位(0无定位1有定位)
     */
    private Integer hasPoint;
    /**
     * 作品名称
     */
    private String worksTitle;
    /**
     * 图片数量
     */
    private Integer imgNumber;
    /**
     * 第一个图片地址
     */
    private String imgUrl;
    /**
     * 图片说明
     */
    private String imgRemark;
    /**
     * 拍摄时间
     */
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
    private String takenAuthor;
    /**
     * 拍摄工具
     */
    private String takenTool;
    /**
     * 二记
     */
    private String content;
    /**
     * 问题1
     */
    private String answerOne;
    /**
     * 问题2
     */
    private String answerTwo;
    /**
     * 问题3
     */
    private String answerThree;
    /**
     * 问题4
     */
    private String answerFour;
    /**
     * 问题5
     */
    private String answerFive;
    /**
     * 问题6
     */
    private String answerSix;

    /**
     * 是否擂主回复
     */
    private Integer championReply;

    private Integer detailsDelete;
    // 1一张   2两张 3三张
    private Integer showType;
}
