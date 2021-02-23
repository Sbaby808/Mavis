package com.mavis.chattingroom.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.mavis.chattingroom.pojo.ResponseEntity;
import com.mavis.chattingroom.service.DemoService;
import com.mavis.chattingroom.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DruidStatController {
    @Autowired
    DemoService demoService;

    @GetMapping("/druidstat")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

    @GetMapping("get/name")
    public ResponseEntity getDataBaseName() {
        ResponseEntity response = new ResponseEntity();
        response.setMessage(MessageConstant.SUCCESS);
        response.setData(demoService.getDatabaseName());
        return response;
    }
}