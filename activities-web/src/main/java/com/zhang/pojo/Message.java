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
 * (Message)实体类
 *
 * @author Distance
 * @since 2020-10-20 10:39:17
 */
@Data
@Getter
@Setter
public class Message implements Serializable {
    private static final long serialVersionUID = 136598410959543547L;


    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer pid;
    
    private String author;
    
    private String email;
    
    private String content;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    
    private String replyname;

    @TableField(exist = false)
    private List<Message> childrenList;
}