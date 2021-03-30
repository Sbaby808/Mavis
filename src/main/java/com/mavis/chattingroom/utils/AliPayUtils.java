package com.mavis.chattingroom.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Sbaby
 * @Date 2021/3/1 23:26
 * @Version 1.0
 */
@Slf4j
public class AliPayUtils {

    private static AlipayClient alipayClient;

    public static AlipayUserInfoShareResponse userInfo(String appId, String priKey, String pubKey, String authCode) throws AlipayApiException {
        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,priKey,"json","GBK",pubKey,"RSA2");
        AlipaySystemOauthTokenResponse tokenRes = getToken(authCode);
        String token = tokenRes.getAccessToken();
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = alipayClient.execute(request,token);
        if(response.isSuccess()){
            log.info("获取Alipay会员信息成功！");
        } else {
            log.error("获取Alipay会员信息异常！");
        }
        return response;
    }

    public static AlipaySystemOauthTokenResponse getToken(String authCode) {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCode);
        AlipaySystemOauthTokenResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("换取AlipayToken异常！");
            e.printStackTrace();
        }
        if(response.isSuccess()){
            log.info("换取Alipay认证token成功！");
        } else {
            log.error("换取Alipay认证token异常！");
        }
        return response;
    }
}
