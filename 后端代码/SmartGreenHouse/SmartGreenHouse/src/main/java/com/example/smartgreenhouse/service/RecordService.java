package com.example.smartgreenhouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.smartgreenhouse.entity.AgriculturalRecordsDao;
import com.example.smartgreenhouse.entity.DeviceDao;
import com.example.smartgreenhouse.entity.FertilizerRecordsDao;
import com.example.smartgreenhouse.entity.HouseDao;
import com.example.smartgreenhouse.mapper.AgriculturalRecordsDaoMapper;
import com.example.smartgreenhouse.mapper.DeviceDaoMapper;
import com.example.smartgreenhouse.mapper.FertilizerRecordsMapper;
import com.example.smartgreenhouse.mapper.HouseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/27 16:31
 */
@Service
public class RecordService {
    //记录类的服务类
    @Resource
    //肥药记录类
    private FertilizerRecordsMapper fertilizerRecordsMapper;
    @Resource
    //农事记录类
    private AgriculturalRecordsDaoMapper agriculturalRecordsDaoMapper;
    @Resource
    private DeviceDaoMapper deviceDaoMapper;
    @Resource
    private HouseMapper houseMapper;

    //肥药记录
    public Integer recordFertilizer(String action, String fertilizer, String device, String cropper,Date time)
    {
        FertilizerRecordsDao fertilizerRecordsDao=new FertilizerRecordsDao();
        fertilizerRecordsDao.setAction(action);
        fertilizerRecordsDao.setFertilizer(fertilizer);
        fertilizerRecordsDao.setDevice(device);
        fertilizerRecordsDao.setCropper(cropper);
        fertilizerRecordsDao.setTime(time);
        Integer is_true=fertilizerRecordsMapper.insert(fertilizerRecordsDao);
        if(is_true==1)
        {
            return 1;
        }
        else {
            return 0;
        }
    }
    //根据作物名返回肥药记录
    public List<FertilizerRecordsDao> showFertilizerRecords(String cropper)
    {
        QueryWrapper<FertilizerRecordsDao> fertilizerRecordsDaoQueryWrapper=new QueryWrapper<>();
        fertilizerRecordsDaoQueryWrapper.eq("cropper",cropper);
        List<FertilizerRecordsDao> fertilizerRecordsDaos=new ArrayList<>();
        fertilizerRecordsDaos=fertilizerRecordsMapper.selectList(fertilizerRecordsDaoQueryWrapper);
        return fertilizerRecordsDaos;
    }

    //农事记录
    public Integer recordAgricultural(String action, String cropper, Date time)
    {
        AgriculturalRecordsDao agriculturalRecordsDao=new AgriculturalRecordsDao();
        agriculturalRecordsDao.setAction(action);
        agriculturalRecordsDao.setCropper(cropper);
        agriculturalRecordsDao.setTime(time);
       Integer is_true=agriculturalRecordsDaoMapper.insert(agriculturalRecordsDao);
       if(is_true==1)
       {
           return 1;
       }
       else {
           return 0;
       }
    }
    //根据作物名返回农事记录
    public List<AgriculturalRecordsDao> showAgriculturalRecords(String cropper)
    {
        QueryWrapper<AgriculturalRecordsDao> agriculturalRecordsDaoQueryWrapper=new QueryWrapper<>();
        agriculturalRecordsDaoQueryWrapper.eq("cropper",cropper);
        List<AgriculturalRecordsDao> agriculturalRecordsDaos=new ArrayList<>();
        agriculturalRecordsDaos=agriculturalRecordsDaoMapper.selectList(agriculturalRecordsDaoQueryWrapper);
        return agriculturalRecordsDaos;
    }
    //硬件自动添加农事记录
    public void AutoaddAgriculturalRecords(Long id, String action) throws ParseException {
        QueryWrapper<DeviceDao> deviceDaoQueryWrapper=new QueryWrapper<>();
        deviceDaoQueryWrapper.eq("id",id);
        DeviceDao deviceDao=deviceDaoMapper.selectOne(deviceDaoQueryWrapper);
        QueryWrapper<HouseDao> houseDaoQueryWrapper=new QueryWrapper<>();
        houseDaoQueryWrapper.eq("name",deviceDao.getHouse());
        HouseDao houseDao=houseMapper.selectOne(houseDaoQueryWrapper);
        //获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());
        Date currentTimeDate = sdf.parse(currentTime);
        //存取农事记录
        AgriculturalRecordsDao agriculturalRecordsDao=new AgriculturalRecordsDao();
        agriculturalRecordsDao.setTime(currentTimeDate);
        agriculturalRecordsDao.setAction(action);
        agriculturalRecordsDao.setCropper(houseDao.getCropper());
        agriculturalRecordsDaoMapper.insert(agriculturalRecordsDao);
    }
    //硬件自动添加肥药记录
    public void AutoaddFertilizerRecords(Long id,String action) throws ParseException {
        QueryWrapper<DeviceDao> deviceDaoQueryWrapper=new QueryWrapper<>();
        deviceDaoQueryWrapper.eq("id",id);
        DeviceDao deviceDao=deviceDaoMapper.selectOne(deviceDaoQueryWrapper);
        QueryWrapper<HouseDao> houseDaoQueryWrapper=new QueryWrapper<>();
        houseDaoQueryWrapper.eq("name",deviceDao.getHouse());
        HouseDao houseDao=houseMapper.selectOne(houseDaoQueryWrapper);
        //获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());
        Date currentTimeDate = sdf.parse(currentTime);
        //存取肥药记录
        FertilizerRecordsDao fertilizerRecordsDao=new FertilizerRecordsDao();
        fertilizerRecordsDao.setFertilizer(deviceDao.getContent());
        fertilizerRecordsDao.setDevice(deviceDao.getName());
        fertilizerRecordsDao.setCropper(houseDao.getCropper());
        fertilizerRecordsDao.setTime(currentTimeDate);
        fertilizerRecordsDao.setAction(action);
        fertilizerRecordsMapper.insert(fertilizerRecordsDao);
    }

}
