package com.mavis.chattingroom.controller.control;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.mavis.chattingroom.config.websocket.SystemWsClient;
import com.mavis.chattingroom.constants.AppParamRedisKeys;
import com.mavis.chattingroom.utils.AliPayUtils;
import com.mavis.chattingroom.utils.jedis.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Sbaby
 * @Date 2021/3/5 23:17
 * @Version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/wscontrol")
public class WebSocketController {

    @Autowired
    private SystemWsClient systemWsClient;
    @Autowired
    JedisUtils jedisUtils;

    /**
     * 支付宝扫码登录回调接口
     * 通过websocket控制前端路由跳转
     * @param app_id
     * @param source
     * @param scope
     * @param auth_code
     * @param topic
     */
    @GetMapping("/login_callback")
    public void routeJumpForLogin(String app_id, String source, String scope, String auth_code, String topic) {
        Map<String, String> authInfo = new HashMap<>();
        authInfo.put("appId", app_id);
        authInfo.put("scope", scope);
        authInfo.put("authCode", auth_code);
        authInfo.put("source", source);
        try {
            AlipayUserInfoShareResponse userInfo = AliPayUtils.userInfo(app_id, jedisUtils.get(AppParamRedisKeys.APPLICATION_PARAM_KEY_APP_PRI),
                    jedisUtils.get(AppParamRedisKeys.APPLICATION_PARAM_KEY_ALIPAY_PUB), auth_code);
        } catch (AlipayApiException e) {
            log.error("Alipay获取会员信息异常！");
            e.printStackTrace();
        }
        systemWsClient.appointSending(topic, authInfo);
    }

}
