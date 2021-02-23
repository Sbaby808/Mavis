package com.mavis.chattingroom.controller;

import com.mavis.chattingroom.pojo.ResponseEntity;
import com.mavis.chattingroom.service.DemoService;
import com.mavis.chattingroom.util.MessageConstant;
import com.mavis.chattingroom.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xinjignjie
 */
@RestController
public class MainController {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 为什么死循环啊啊啊啊啊啊
     *
     * @return
     */
//    @GetMapping("/go/{path}")
//    public ModelAndView go(@PathVariable("path") String paths) {
//        System.out.println(paths);
//        return new ModelAndView(paths);
//    }
    @GetMapping("/a")
    public ModelAndView a() {
        return new ModelAndView("a");
    }

    @GetMapping("/websocket")
    public ModelAndView websocket() {
        return new ModelAndView("websocket");
    }

    @GetMapping("/set")
    public ResponseEntity setKey(@RequestParam("key") String key, @RequestParam("value") String value) {
        return redisUtil.set(key, value) ? ResponseEntity.success() : ResponseEntity.failure();
    }

    @GetMapping("/get")
    public ResponseEntity getKey(@RequestParam("key") String key) {
        ResponseEntity response = ResponseEntity.success();
        return response.setData(redisUtil.get(key));
    }

    @GetMapping("/setAndExpireTime")
    public ResponseEntity setExpireTime(@RequestParam("key") String key, @RequestParam("time") long time) {
        return redisUtil.expire(key, time) ? ResponseEntity.success() : ResponseEntity.failure();
    }
}
