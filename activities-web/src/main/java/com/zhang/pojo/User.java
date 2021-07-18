package com.zhang.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


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

    
    private Integer id;
    
    private String username;
    
    private String password;
    
    private String realname;
    
    private String sex;
    
    private String phone;
    
    private String email;
    
    private String birthday;
    
    private String skill;
    
    private String evaluate;
    
    private String photo;

    private String role;

}