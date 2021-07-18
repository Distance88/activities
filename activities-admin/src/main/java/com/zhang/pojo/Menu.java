package com.zhang.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * 菜单权限表(Menu)实体类
 *
 * @author Distance
 * @since 2021-03-22 15:46:05
 */
@Data
@Getter
@Setter
public class Menu implements Serializable {
    private static final long serialVersionUID = -25249020611665778L;

    /**
    * 菜单ID
    */
    private Integer id;
    /**
    * 菜单名称
    */
    private String name;
    /**
    * 父菜单ID
    */
    private Integer parentId;
    /**
    * 显示顺序
    */
    private Integer orderNum;
    /**
    * 请求地址
    */
    private String url;
    /**
    * 菜单类型（M目录 C菜单 F按钮）
    */
    @TableField(select = false)
    private String type;
    /**
    * 菜单状态（0显示 1隐藏）
    */
    @TableField(select = false)
    private String visible;
    /**
    * 权限标识
    */
    private String perms;
    /**
    * 菜单图标
    */
    private String icon;
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
    private String updateTime;
    /**
    * 备注
    */
    private String remark;

    @TableField(exist = false)
    private List<Menu> children;
}