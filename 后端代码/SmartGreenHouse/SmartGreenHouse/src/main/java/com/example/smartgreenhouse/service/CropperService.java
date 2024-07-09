package com.example.smartgreenhouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.smartgreenhouse.entity.CropperDao;
import com.example.smartgreenhouse.mapper.CropperMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@Service
public class CropperService {

    @Resource
    private CropperMapper cropperMapper;

    //添加种植物
    public Integer addCropper(String name, String house, Date plantTime, String picUrl)
    {
        CropperDao cropperDao=new CropperDao();
        cropperDao.setName(name);
        cropperDao.setHouse(house);
        cropperDao.setPlantTime(plantTime);
        cropperDao.setPicUrl(picUrl);
        Integer is_true=cropperMapper.insert(cropperDao);
        if(is_true==1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    //返回种植物信息
    public CropperDao cropperDaoInfo(String name)
    {
        QueryWrapper<CropperDao> cropperDaoQueryWrapper=new QueryWrapper<>();
        cropperDaoQueryWrapper.eq("name",name);
        CropperDao cropperDao=cropperMapper.selectOne(cropperDaoQueryWrapper);
        if(cropperDao!=null)
        {
            return cropperDao;
        }
        else
        {
            return null;
        }
    }
    //删除种植物
    public Integer deleteCropper(Long id)
    {
        QueryWrapper<CropperDao> cropperDaoQueryWrapper=new QueryWrapper<>();
        cropperDaoQueryWrapper.eq("id",id);
        Integer is_true=cropperMapper.delete(cropperDaoQueryWrapper);
        if(is_true==1)
        {
            return 1;
        }
        else {
            return 0;
        }
    }


}
