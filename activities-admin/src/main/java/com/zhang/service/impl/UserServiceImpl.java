package com.zhang.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.mapper.DeptMapper;
import com.zhang.mapper.MenuMapper;
import com.zhang.mapper.UserMapper;
import com.zhang.pojo.Dept;
import com.zhang.pojo.Menu;
import com.zhang.pojo.User;
import com.zhang.restful.Response;
import com.zhang.service.UserService;
import com.zhang.utils.OssUtil;
import com.zhang.utils.RedisUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * (User)表服务接口
 *
 * @author Distance
 * @since 2020-10-26 16:21:40
 */
@Service
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {

    private static String urlPrefix = "https://ngyst.oss-cn-hangzhou.aliyuncs.com/";

    @Resource
    private UserMapper userMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public User findUserByUserName(String username) {

        String key = "activities_user_"+username;

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return (User) redisUtil.get(key);

        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));



        redisUtil.set(key,user);

        return user;
    }

    @Override
    public Page<User> findUserList(Integer current) {


        Page<User> page = new Page<>(current,5);


        return (Page<User>) userMapper.selectPage(page,null);
    }

    @Override
    public List<User> findUserByCondition(User user) {


        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        wrapper.like("username",user.getKeyWorld())
                .or()
                .between("create_time",user.getStartTime(),user.getEndTime())
                .or()
                .like("nickname",user.getKeyWorld());

        List<User> userList = userMapper.selectList(wrapper);

        return userList;
    }

    @Override
    public List<User> findUserByDeptId(Integer deptId) {

        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();

        wrapper.select("id").like("ancestors", deptId);

        List<Dept> deptList = deptMapper.selectList(wrapper);
        List<Integer> ids = new ArrayList<>();
        if(deptList.size() == 0){
            ids.add(deptId);
        }


        for(int i=0;i<deptList.size();i++){

            ids.add(deptList.get(i).getId());
        }
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().in("dept_id",ids));
        return userList;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public User updateUserInfo(User user) {

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();

        wrapper.set("phone",user.getPhone())
               .set("email",user.getEmail())
               .eq("username",user.getUsername());


        redisUtil.del("activities_user_"+user.getUsername());

        userMapper.update(user,wrapper);




        return findUserByUserName(user.getUsername());
    }

    @Override
    public User updateAvatar(MultipartFile multipartFile,String username) {

        OSS ossClient = OssUtil.getOssClient();

        Calendar calendar = Calendar.getInstance();


        String imagePath = calendar.get(Calendar.YEAR) +
                "/" + StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0')+
                "/" + StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0')+
                "/" + UUID.randomUUID().toString() + "." +  multipartFile.getOriginalFilename().split("\\.")[1];

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest("ngyst",imagePath,new ByteArrayInputStream(multipartFile.getBytes()));
            ossClient.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }

        String avatar = urlPrefix+imagePath;

        userMapper.updateAvatar(avatar,username);

        redisUtil.del("activities_user_"+username);

        return findUserByUserName(username);
    }
}