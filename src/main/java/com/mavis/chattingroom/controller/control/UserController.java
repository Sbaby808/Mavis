package com.mavis.chattingroom.controller.control;

import com.mavis.chattingroom.entity.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Sbaby
 * @Date 2021/3/29 18:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 初始化登录信息
     * @param appId
     * @param source
     * @param authCode
     * @param scope
     * @return
     */
    public Response initInfo(@RequestParam("appId") String appId,
                             @RequestParam("source") String source,
                             @RequestParam("authCode") String authCode,
                             @RequestParam("scope") String scope) {
        return null;
    }
}
