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
 * (Problem)实体类
 *
 * @author Distance
 * @since 2020-10-15 20:40:06
 */
@Data
@Getter
@Setter
public class Problem implements Serializable {
    private static final long serialVersionUID = 847191116033063739L;


    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String title;
    
    private String level;
    
    private String description;
    
    private String input;
    
    private String output;
    
    private String sampleinput;
    
    private String sampleoutput;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

}