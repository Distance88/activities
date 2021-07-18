package com.zhang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * 部门表(Dept)实体类
 *
 * @author Distance
 * @since 2021-05-21 13:11:28
 */
@Data
@Getter
@Setter
public class Dept implements Serializable {
    private static final long serialVersionUID = 967016237901505033L;

    /**
    * 部门id
    */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 父部门id
    */
    private Integer parentId;
    /**
    * 祖级列表
    */
    private String ancestors;
    /**
    * 部门名称
    */
    private String name;
    /**
    * 显示顺序
    */
    private Integer orderNum;
    /**
    * 负责人
    */
    @TableField(select = false)
    private String leader;
    /**
    * 联系电话
    */
    @TableField(select = false)
    private String phone;
    /**
    * 邮箱
    */
    @TableField(select = false)
    private String email;
    /**
    * 部门状态（0正常 1停用）
    */
    private String status;
    /**
    * 创建者
    */
    private String createBy;
    /**
    * 创建时间
    */
    private String createTime;

    @TableField(exist = false)
    private List<Dept> children;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

}