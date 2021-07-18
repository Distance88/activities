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
 * (Blog)实体类
 *
 * @author Distance
 * @since 2020-11-06 10:21:20
 */
@Data
@Getter
@Setter
public class Blog implements Serializable {
    private static final long serialVersionUID = 625341659944337798L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String title;

    private String content;

    private String mdContent;

    private String charts;
    
    private String type;
    
    private String label;
    
    private String style;

    private String digest;

    @TableField(fill = FieldFill.INSERT)
    private Integer views;
    
    private String author;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String createTime;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    private Integer userId;
}