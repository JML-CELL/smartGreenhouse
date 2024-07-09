package com.example.smartgreenhouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.smartgreenhouse.entity.DeviceDao;
import com.example.smartgreenhouse.mapper.DeviceDaoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :Kr
 * @date :2024/3/12 16:47
 */
@Service
public class DeviceService {

    @Resource
    private DeviceDaoMapper deviceDaoMapper;
    //添加设备
    public void add(String name,String content,String house)
    {
        DeviceDao deviceDao=new DeviceDao();
        deviceDao.setName(name);
        deviceDao.setContent(content);
        deviceDao.setHouse(house);
        deviceDaoMapper.insert(deviceDao);
    }
    //删除设备
    public void delete(Long id)
    {
        deviceDaoMapper.deleteById(id);
    }
    //返回设备id
    public Long returnId(String name,String house)
    {
        QueryWrapper<DeviceDao> deviceDaoQueryWrapper=new QueryWrapper<>();
        deviceDaoQueryWrapper.eq("name",name).eq("house",house);
        Long id=deviceDaoMapper.selectCount(deviceDaoQueryWrapper);
        return id;
    }
}
