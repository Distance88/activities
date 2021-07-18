package com.zhang.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * (Info)实体类
 *
 * @author Distance
 * @since 2020-10-15 20:32:05
 */
@Data
@Getter
@Setter
public class Info implements Serializable {
    private static final long serialVersionUID = 646210621871371551L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String title;
    
    private String content;
    
    private String style;
    
    private String author;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT)
    private Integer views;

    private String digest;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;
}