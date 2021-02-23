package com.mavis.chattingroom.controller;

import com.mavis.chattingroom.pojo.ResponseEntity;
import com.mavis.chattingroom.service.DemoService;
import com.mavis.chattingroom.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xinjignjie
 */
@RestController
public class MainController {

    /**
     * 为什么死循环啊啊啊啊啊啊
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
}
