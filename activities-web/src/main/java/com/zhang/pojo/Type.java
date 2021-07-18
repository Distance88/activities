package com.zhang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * (Type)实体类
 *
 * @author Distance
 * @since 2020-11-07 09:52:27
 */
@Data
@Getter
@Setter
public class Type implements Serializable {
    private static final long serialVersionUID = -57919331275719978L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String name;
    
    private Integer count;

}