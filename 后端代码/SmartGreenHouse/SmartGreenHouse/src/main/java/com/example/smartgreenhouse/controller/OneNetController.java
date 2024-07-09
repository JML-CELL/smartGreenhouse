package com.example.smartgreenhouse.controller;

import com.example.smartgreenhouse.entity.Datalist;
import com.example.smartgreenhouse.entity.HistoricalDataList;
import com.example.smartgreenhouse.entity.JsonRootBean;
import com.example.smartgreenhouse.service.OneNetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author :Kr
 * @date :2024/1/30 9:09
 */
@RestController
@RequestMapping("/oneNet")
@Api(tags = "OneNet数据获取")
public class OneNetController {

    @Autowired
    private OneNetService oneNetService;

    @GetMapping("/getOnenetLatestdata")
    @ApiOperation("获取最新数据，返回的是json格式")
    public JsonRootBean getOnenetLatestdata() throws IOException {
        return oneNetService.getOnenetLatestdata();
    }
    @GetMapping("/getAirHistoricalData")
    @ApiOperation("获取历史数据，时间跨度不能超过七天")
    public List<HistoricalDataList> getAirHistoricalData(@ApiParam(value = "开始时间") String start_time, @ApiParam(value = "结束时间") String end_time,@ApiParam
            (value = "输入要获取数值的参数")String identifier,@ApiParam(value = "选择模式，1的话返回的是近七天的数值的平均值，0的话是返回当天按小时的数值") Integer model) throws IOException, ParseException {
        return oneNetService.airHistoricalData(start_time,end_time,identifier,model);
    }
}
