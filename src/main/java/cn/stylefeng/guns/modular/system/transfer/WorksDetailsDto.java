package cn.stylefeng.guns.modular.system.transfer;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 作品信息
 * Created by lyc on 2018/12/22.
 */
@Data
public class WorksDetailsDto {

    /**
     * id
     */
    String id;
    /**
     * 活动ID
     */
    String activityId;
    /**
     * 用户ID
     */
    Long uid;
    /**
     * 评论数量
     */
    Integer replyNumber;
    /**
     * 转发数量
     */
    Integer forwardNumber;
    /**
     * 点赞数量
     */
    Integer giveLikeNumber;
    /**
     * 创建时间
     */
    Date createTime;
    /**
     * 审核状态(0待审核1通过-1不通过)
     */
    Integer state;
    /**
     * 是否定位(0无定位1有定位)
     */
    Integer hasPoint;
    /**
     * 作品名称
     */
    String worksTitle;
    /**
     * 图片数量
     */
    Integer imgNumber;
    /**
     * 第一个图片地址
     */
    String imgUrl;
    /**
     * 图片说明
     */
    String imgRemark;
    /**
     * 拍摄时间
     */
    Date pohtoTime;
    /**
     * 天气
     */
    String weather;
    /**
     * 拍摄地点
     */
    String address;
    /**
     * 拍摄作者
     */
    String takenAuthor;
    /**
     * 拍摄工具
     */
    String takenTool;
    /**
     * 二记
     */
    String content;
    /**
     * 问题1
     */
    String answerOne;
    /**
     * 问题2
     */
    String answerTwo;
    /**
     * 问题3
     */
    String answerThree;
    /**
     * 问题4
     */
    String answerFour;
    /**
     * 问题5
     */
    String answerFive;
    /**
     * 问题6
     */
    String answerSix;

    /**
     * 是否擂主回复
     */
    Integer championReply;

    /**
     * 是否删除 0位删除 1已删除
     */
    Integer detailsDelete;

    /**
     * 问题图片集和
     */
    List<WorksImgDetailsDto> worksImgDetailsList;

    /**
     * 联系方式
     */
    String contact;
    /**
     * 所在学校
     */
    String authorSchool;
    /**
     * 作者年级
     */
    String authorAge;
    /**
     * 辅导老师
     */
    String authorTeacher;
}
