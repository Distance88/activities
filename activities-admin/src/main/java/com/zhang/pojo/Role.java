package com.zhang.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 角色信息表(Role)实体类
 *
 * @author Distance
 * @since 2021-05-21 13:33:54
 */
@Data
@Getter
@Setter
public class Role implements Serializable {
    private static final long serialVersionUID = 164289769080577053L;

    /**
    * 角色ID
    */
    private Long id;
    /**
    * 角色名称
    */
    private String name;
    /**
    * 角色权限字符串
    */
    private String permkey;
    /**
    * 显示顺序
    */
    private Integer sort;
    /**
    * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
    */
    private String scope;
    /**
    * 角色状态（0正常 1停用）
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
    /**
    * 备注
    */
    private String remark;
}