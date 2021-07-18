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
 * (Comments)实体类
 *
 * @author Distance
 * @since 2021-06-06 16:13:32
 */
@Data
@Getter
@Setter
public class Comments implements Serializable {
    private static final long serialVersionUID = 572749306217246186L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer pid;
    
    private String author;
    
    private String content;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    
    private String replyname;
    
    private Integer blogId;

    @TableField(exist = false)
    private List<Comments> childrenList;
}