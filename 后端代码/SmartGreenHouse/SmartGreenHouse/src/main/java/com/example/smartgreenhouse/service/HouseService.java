package com.example.smartgreenhouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.smartgreenhouse.entity.HouseDao;
import com.example.smartgreenhouse.mapper.HouseMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@Service
public class HouseService {

    @Resource
    private HouseMapper houseMapper;

    //添加温室
    public Integer addHouse(String name,Integer length,Integer wide,Integer high,String cropper,String housePic)
    {
        HouseDao houseDao=new HouseDao();
        houseDao.setName(name);
        houseDao.setLength(length);
        houseDao.setWide(wide);
        houseDao.setHigh(high);
        houseDao.setCropper(cropper);
        houseDao.setHousePic(housePic);
        Integer is_true=houseMapper.insert(houseDao);
        if(is_true==1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    //返回基本温室信息
    public HouseDao houseInfo(String name)
    {
        QueryWrapper<HouseDao> houseDaoQueryWrapper=new QueryWrapper<>();
        houseDaoQueryWrapper.eq("name",name);
        HouseDao houseDao=houseMapper.selectOne(houseDaoQueryWrapper);
        if(houseDao!=null)
        {
            return houseDao;
        }
        else {
            return null;
        }
    }
    //删除温室
    public Integer deleteHouse(Long id)
    {
        QueryWrapper<HouseDao> houseDaoQueryWrapper=new QueryWrapper<>();
        houseDaoQueryWrapper.eq("id",id);
        Integer is_true=houseMapper.delete(houseDaoQueryWrapper);
        if(is_true==1)
        {
            return 1;
        }
        else {
            return 0;
        }
    }
    //返回所有温室的信息
    public List<HouseDao> allShowHouse()
    {
        List<HouseDao> houseDaoList=new ArrayList<>();
        houseDaoList=houseMapper.selectList(null);
        return houseDaoList;
    }
}
