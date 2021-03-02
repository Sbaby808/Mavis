package com.mavis.chattingroom.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayUserInfoShareResponse;

/**
 * @Author Sbaby
 * @Date 2021/3/1 23:26
 * @Version 1.0
 */
public class AliPayUtils {

    public static void userInfo(String appId, String priKey, String pubKey, String token) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,priKey,"json","GBK",pubKey,"RSA2");
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = alipayClient.execute(request,token);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        System.out.println(response);
    }
}
