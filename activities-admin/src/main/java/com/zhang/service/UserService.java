package com.zhang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author Distance
 * @since 2020-10-26 16:21:40
 */
public interface UserService {

    User findUserByUserName(String username);


    Page<User> findUserList(Integer current);

    List<User> findUserByCondition(User user);

    List<User> findUserByDeptId(Integer deptId);

    void insertUser(User user);

    void deleteById(Integer id);

    User updateUserInfo(User user);

    User updateAvatar(MultipartFile multipartFile,String username);
}