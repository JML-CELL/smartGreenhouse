package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.UserDao;
import com.example.smartgreenhouse.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理相关接口")
public class UserController {

     @Autowired
     private UserService userService;
    @GetMapping("/enRoll")
    @ApiOperation("注册，成功返回1 不成功返回0")
    public Integer enRoll( @ApiParam(name = "username",value = "用户名")String username,@ApiParam(name = "password",value = "密码")String password,@ApiParam(value = "qq邮箱") String qqMail)
    {
        return userService.enRoll(username,password,qqMail);
    }
    @GetMapping("/login")
    @ApiOperation("登录，成功返回1，不成功返回0")
    public Integer login(@ApiParam(name = "qqMail",value = "qq邮箱")String qqMail,@ApiParam(name = "password",value = "密码")String password)
    {
        return userService.login(qqMail,password);
    }
    @GetMapping("/userInfo")
    @ApiOperation("返回用户信息")
    public UserDao userInfo(@ApiParam(value = "QQ邮箱") String qqMail)
    {
        return userService.userInfo(qqMail);
    }
    @GetMapping("/upPicUrl")
    @ApiOperation("上传用户头像")
    public void upPicUrl(String url,Long id)
    {
       userService.upPicUrl(url, id);
    }

    @GetMapping("/modify")
    @ApiOperation("修改用户密码")
    public void modify(@ApiParam(value = "用户id") Long id,@ApiParam(value = "用户密码") String password)
    {
        userService.modify(id, password);
    }

    @GetMapping("/modifyName")
    @ApiOperation("修改用户名")
    public void modifyName(@ApiParam(value = "用户id")Long id,@ApiParam(value = "用户名")String name)
    {
        userService.modifyName(id, name);
    }
    @GetMapping("/modifyQQmail")
    @ApiOperation("修改用户邮箱")
    public void modify_qqmail(@ApiParam(value = "用户id")Long id,@ApiParam(value = "用户邮箱")String qqMail)
    {
        userService.modify_qqmail(id, qqMail);
    }

}
