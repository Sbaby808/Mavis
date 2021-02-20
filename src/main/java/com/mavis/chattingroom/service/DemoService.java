package com.mavis.chattingroom.service;

import com.mavis.chattingroom.dao.DemoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author： xinjingjie
 * @Date：2021/1/26 15:22
 **/
@Service
public class DemoService {

    Logger logger= LoggerFactory.getLogger(DemoService.class);

    @Autowired
    DemoDao demoDao;

    public String getDatabaseName(){
        return demoDao.getDataBaseName();
    }
}
