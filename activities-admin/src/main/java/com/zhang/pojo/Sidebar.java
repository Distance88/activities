package com.zhang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * (Sidebar)实体类
 *
 * @author Distance
 * @since 2021-03-18 09:21:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sidebar implements Serializable {
    private static final long serialVersionUID = 512990470870342182L;


    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String path;
    
    private String name;
    
    private String title;
    
    private String icon;
    
    private Integer parentId;

    @TableField(exist = false)
    private List<Sidebar> children;

    private String roleId;
}