package com.mavis.chattingroom.controller;

import com.mavis.chattingroom.pojo.ResponseEntity;
import com.mavis.chattingroom.service.DemoService;
import com.mavis.chattingroom.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xinjignjie
 */
@RestController
public class MainController {
    @Autowired
    DemoService demoService;

    @GetMapping("get/name")
    public ResponseEntity getDataBaseName() {
        ResponseEntity response = new ResponseEntity();
        response.setMessage(MessageConstant.SUCCESS);
        response.setData(demoService.getDatabaseName());
        return response;
    }
}
