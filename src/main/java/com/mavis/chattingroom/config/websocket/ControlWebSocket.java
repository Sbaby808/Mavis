package com.mavis.chattingroom.config.websocket;

import com.alibaba.fastjson.JSONObject;
import com.mavis.chattingroom.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Sbaby
 * @Date 2021/3/5 22:33
 * @Version 1.0
 */
@Slf4j
@ServerEndpoint(value = "/mavis/{topic}")
@Component
public class ControlWebSocket {

    @Autowired
    WebSocketClient webSocketClient;

    private String topic;
    private Session session;

    /**
     * 用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, ControlWebSocket> webSocketSet = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("topic") String topic) {
        this.session = session;
        this.topic = topic;
        webSocketSet.put(topic, this);
        log.info("[Topic:" + this.topic + "] WebSocket连接成功！");
//        this.session.getAsyncRemote().sendText(JSONObject.toJSONString(
//                new Response().success("[Topic:" + this.topic + "] WebSocket连接成功！" )));
    }

    /**
     * 连接关闭调用的方法    
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this.topic);
        log.debug("[Topic:" + this.topic + "] WebSocket连接关闭！");
        /*
         * 这里如果再调下面的方法的话，会报null错误
         * this.session.getAsyncRemote().sendText(JSONObject.toJSONString(
         *      new Response().failure("[Topic:" + this.topic + "] WebSocket连接关闭！")));
         */
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("topic") String topic) {
        log.debug("[Topic:" + this.topic + "] WebSocket收到消息：" + message + "！");
        if(message.startsWith("TO[")) {
            String toTopic = message.substring(3, message.indexOf("]"));
            String sendMsg = message.substring(message.indexOf("]") + 1);
            appointSending(toTopic, sendMsg);
        } else {

        }
    }

    /**
     * 发生错误时调用   
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("[Topic:" + this.topic + "] WebSocket错误！");
        this.session.getAsyncRemote().sendText(JSONObject.toJSONString(
                new Response().failure("[Topic:" + this.topic + "] WebSocket错误！")));
        error.printStackTrace();
    }

    /**
     * 向单个客户端发送消息
     * @param topic
     * @param message
     */
    public void appointSending(String topic, String message) {
        try {
            webSocketSet.get(topic).session.getBasicRemote().sendText(message);
            log.info("[Topic:" + topic + "] 发送消息" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
