package com.zhang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.mapper.UserMapper;
import com.zhang.pojo.Menu;
import com.zhang.pojo.User;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.SidebarService;
import com.zhang.service.impl.UserServiceImpl;
import com.zhang.utils.JwtUtils;
import com.zhang.utils.RedisUtil;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author Distance
 * @since 2020-10-26 16:21:40
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private SidebarService sidebarService;


    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("login")
    public JSONObject login(User user) throws UnsupportedEncodingException {

        JSONObject object = new JSONObject();


        UsernamePasswordToken up_token = new UsernamePasswordToken(user.getUsername(),user.getPassword());

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(up_token);

            boolean hasKey = redisUtil.hasKey(user.getUsername());
            String access_token = JwtUtils.sign(user.getUsername(), "123456",30 * 60 * 1000);
            String refresh_token = JwtUtils.sign(user.getUsername(), "123456",60* 60 * 1000);

            if(hasKey){
                redisUtil.del(user.getUsername());
                redisUtil.set(user.getUsername(),access_token);
            }else{
                redisUtil.set(user.getUsername(),access_token);
            }

            User user1 = userService.findUserByUserName(user.getUsername());
            object.put("code",200);
            object.put("msg","登陆成功");
            object.put("access_token",access_token);
            object.put("refresh_token",refresh_token);
            object.put("sideBar",sidebarService.findAllSideBarByRoleId(user1.getRoleId()));
            object.put("user",user1);

        }catch (UnknownAccountException e){
            object.put("code",500);
            object.put("msg","用户不存在");
        }catch (IncorrectCredentialsException e){
            object.put("code",500);
            object.put("msg","用户或密码错误");
        }

        return object;

    }

    @RequestMapping("getUserList")
    public ResponseResult<Page<User>> getUserList(Integer current){

        return Response.makeOKRsp(userService.findUserList(current));
    }

    @RequestMapping("findUserByCondition")
    public ResponseResult<List<User>> findUserByCondition(User user){
        return Response.makeOKRsp(userService.findUserByCondition(user));
    }

    @RequestMapping("findUserByDeptId")
    public ResponseResult<List<User>> findUserByDeptName(Integer deptId){

        return Response.makeOKRsp(userService.findUserByDeptId(deptId));
    }

    @RequestMapping("updateUserInfo")
    public ResponseResult<User> updateUserInfo(User user){

        return Response.makeOKRsp(userService.updateUserInfo(user));
    }

    @RequestMapping("deleteById")
    public ResponseResult deleteUserById(Integer id){
        userService.deleteById(id);

        return Response.makeOKRsp();
    }

    @RequestMapping("uploadAvatar")
    public ResponseResult<User> uploadAvatar(@Param("multipartFile")MultipartFile multipartFile,@Param("username") String username){

        return Response.makeOKRsp(userService.updateAvatar(multipartFile,username));
    }
}