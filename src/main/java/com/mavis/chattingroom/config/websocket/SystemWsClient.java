package com.mavis.chattingroom.config.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Sbaby
 * @Date 2021/3/6 11:41
 * @Version 1.0
 */
@Slf4j
@Component
public class SystemWsClient implements WebSocketClientService {

    @Autowired
    private WebSocketClient webSocketClient;

    @Override
    public void groupSending(Object message) {
        String jsonMsg = JSONObject.toJSONString(message);
        webSocketClient.send(jsonMsg);
        log.info("SystemWsClient send to [All]: " + jsonMsg);
    }

    @Override
    public void appointSending(String topic, Object message) {
        String jsonMsg = JSONObject.toJSONString(message);
        webSocketClient.send("TO[" + topic + "]" + jsonMsg);
        log.info("SystemWsClient send to [" + topic + "]: " + jsonMsg);
    }

    public boolean checkConnect(String topic) {
        log.info(webSocketClient.getReadyState().toString());
        if (!webSocketClient.isOpen()) {
            if (webSocketClient.getReadyState().equals(WebSocket.READYSTATE.NOT_YET_CONNECTED)) {
                try {
                    webSocketClient.connect();
                    return true;
                } catch (IllegalStateException e) {
                }
            } else if (webSocketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSING) || webSocketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSED)) {
                webSocketClient.connect();
                return true;
            }
        }
        return false;

    }
}
