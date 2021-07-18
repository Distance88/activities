package com.zhang.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * 岗位信息表(Post)实体类
 *
 * @author Distance
 * @since 2021-03-23 09:44:56
 */
@Data
@Getter
@Setter
public class Post implements Serializable {
    private static final long serialVersionUID = -29897087568488453L;

    /**
    * 岗位ID
    */
    private Integer id;
    /**
    * 岗位编码
    */
    private String code;
    /**
    * 岗位名称
    */
    private String name;
    /**
    * 显示顺序
    */
    private Integer sort;
    /**
    * 状态（0正常 1停用）
    */
    private String status;
    /**
    * 创建者
    */
    @TableField(select = false)
    private String createBy;
    /**
    * 创建时间
    */
    private String createTime;
    /**
    * 更新者
    */
    @TableField(select = false)
    private String updateBy;
    /**
    * 更新时间
    */
    @TableField(select = false)
    private Date updateTime;
    /**
    * 备注
    */
    @TableField(select = false)
    private String remark;
}