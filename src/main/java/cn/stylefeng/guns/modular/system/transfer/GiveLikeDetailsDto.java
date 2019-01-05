package cn.stylefeng.guns.modular.system.transfer;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;

/**
 * Created by lyc on 2019/1/5.
 */
@Data
public class GiveLikeDetailsDto {
    /**
     * 模块
     */
    private String model;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 用户姓名
     */
    private String uname;
    /**
     * 用户头像
     */
    private String photo;
    /**
     * 业务ID
     */
    private String businessId;
    /**
     * 点赞时间
     */
    private Date createTime;
}
