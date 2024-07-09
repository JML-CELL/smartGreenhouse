package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.HouseDao;
import com.example.smartgreenhouse.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/26 0:08
 */
@RestController
@RequestMapping("/house")
@Api(tags = "温室相关接口")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/addHouse")
    @ApiOperation("添加温室，成功返回1，否则0")
    public Integer addHouse(@ApiParam(value = "温室名字") String name,@ApiParam(value = "长度") Integer length,@ApiParam(value = "宽度") Integer wide,@ApiParam(value = "高度") Integer high,@ApiParam(value = "种植物") String cropper,@ApiParam(value = "温室图片地址") String housePic)
    {
        return houseService.addHouse(name,length,wide,high,cropper,housePic);
    }

    @GetMapping("/houseInfo")
    @ApiOperation("返回温室的基本信息 没有则返回null")
    public HouseDao houseInfo(@ApiParam(value = "温室名字") String name)
    {
        return houseService.houseInfo(name);
    }

    @GetMapping("/deleteHouse")
    @ApiOperation("删除温室，成功返回1 否则0")
    public Integer deleteHouse(@ApiParam(value = "温室id") Long id)
    {
        return houseService.deleteHouse(id);
    }

    @GetMapping("/allShowHouse")
    @ApiOperation("返回所有的温室信息")
    public List<HouseDao> allShowHouse()
    {
        return houseService.allShowHouse();
    }
}
