package com.zhang.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * (User)实体类
 *
 * @author Distance
 * @since 2020-11-12 09:52:09
 */
@Data
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = -78184210456858602L;


    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 部门ID
     */
    private Integer deptId;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    private String deptName;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 帐号状态（0正常 1停用）
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
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    /**
     * 备注
     */
    @TableField(select = false)
    private String remark;

    @TableField(exist = false)
    private String KeyWorld;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    private Integer roleId;

    @TableField(exist = false)
    private List<Sidebar> sideBar;


}