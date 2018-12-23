package cn.stylefeng.guns.modular.system.transfer;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;

/**
 * Created by lyc on 2018/12/23.
 */
@Data
public class ReplyDetailsDto {
    /**
     * id
     */
    private String id;
    /**
     * 业务ID
     */
    private String businessId;
    /**
     * 模块
     */
    private String model;
    /**
     * 父评论ID
     */
    private Long parentId;
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
    private Date createTime;
    /**
     * 状态(0待审核1审核通过 -1 不通过)
     */
    private Integer replyState;
    /**
     * 点赞数量
     */
    private Integer giveLikeNumber;
}
