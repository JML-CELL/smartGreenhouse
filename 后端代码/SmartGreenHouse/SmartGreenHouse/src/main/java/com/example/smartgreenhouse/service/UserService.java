package com.example.smartgreenhouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.smartgreenhouse.entity.UserDao;
import com.example.smartgreenhouse.mapper.UserDaoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@Service
public class UserService {

    @Resource
    private UserDaoMapper userDaoMapper;

    //注册
    public Integer enRoll(String username, String password, String qqMail)
    {
        UserDao userDao =new UserDao();
        userDao.setUsername(username);
        userDao.setPassword(password);
        userDao.setQqMail(qqMail);
        Integer is_true= userDaoMapper.insert(userDao);
        if(is_true==1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    //登录
    public Integer login(String qqMail, String password)
    {
        QueryWrapper<UserDao> userDaoQueryWrapper=new QueryWrapper<>();
        userDaoQueryWrapper.eq("qq_mail",qqMail).eq("password",password);
        List<UserDao> userDaoList= userDaoMapper.selectList(userDaoQueryWrapper);
        if(userDaoList.size()!=0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    //返回用户信息
    public UserDao userInfo(String qqMail)
    {
        QueryWrapper<UserDao> userDaoQueryWrapper=new QueryWrapper<>();
        userDaoQueryWrapper.eq("qq_mail",qqMail);
        UserDao userDao=userDaoMapper.selectOne(userDaoQueryWrapper);
        if(userDao!=null)
        {
            return userDao;
        }
        else {
            return null;
        }
    }

    //上传用户头像
    public void upPicUrl(String url,Long id)
    {
        QueryWrapper<UserDao> userDaoQueryWrapper=new QueryWrapper<>();
        userDaoQueryWrapper.eq("id",id);
        UserDao userDao=new UserDao();
        userDao.setUrl(url);
        userDaoMapper.update(userDao,userDaoQueryWrapper);
    }
    //修改用户密码
    public void modify(Long id,String password)
    {
        UserDao userDao=new UserDao();
        userDao.setId(id);
        userDao.setPassword(password);

        userDaoMapper.updateById(userDao);
    }
    //修改用户名
    public void modifyName(Long id,String name)
    {
        UserDao userDao=new UserDao();
        userDao.setId(id);
        userDao.setUsername(name);
        QueryWrapper<UserDao> userDaoQueryWrapper=new QueryWrapper<>();
        userDaoQueryWrapper.eq("id",id);
        userDaoMapper.updateById(userDao);
    }
    //修改用户邮箱
    public void modify_qqmail(Long id,String qqMail)
    {
        UserDao userDao=new UserDao();
        userDao.setId(id);
        userDao.setQqMail(qqMail);
        QueryWrapper<UserDao> userDaoQueryWrapper=new QueryWrapper<>();
        userDaoQueryWrapper.eq("id",id);
        userDaoMapper.updateById(userDao);
    }

}
