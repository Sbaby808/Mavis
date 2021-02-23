package com.mavis.chattingroom.controller;

import com.mavis.chattingroom.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author： xinjingjie
 * @Date：2021/2/20 16:06
 **/
@ServerEndpoint("/chat/{userId}")
@Component
public class WebSocketServer {
    static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger onlineCount = new AtomicInteger();
    private static final ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String userId;

    /**
     * 建立连接
     *
     * @param session
     * @param userId
     */
    @OnOpen
    public void open(Session session, @PathParam("userId") String userId) throws IOException {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            logger.info("已有userId:" + userId + "：重新登录");
        } else {
            webSocketMap.put(userId, this);
            onlineCount.incrementAndGet();
            logger.info("请求的userId:" + userId + "：新加入用户");
        }
        for (String i : webSocketMap.keySet()) {
            webSocketMap.get(i).sendMessage("在线人数：" + getOnlineCount());
        }
    }

    /**
     * 接收消息
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void getMessage(String message, Session session) {
        if (!"".equals(message)) {
            try {
                logger.info(message);
                System.out.println("----");
                //解析发送的报文
                HashMap jsonObject = JsonUtils.parse(message, HashMap.class);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = (String) jsonObject.get("toUserId");
                //传送给对应toUserId用户的websocket
                if (!"".equals(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(JsonUtils.toJsonString(jsonObject));
                    logger.info("toUserId:{},\rjsonObject:{}", toUserId, jsonObject);
                } else {
                    logger.error("请求的userId:" + toUserId + "不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message + "\r\n");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            onlineCount.decrementAndGet();
        }
        for (String i : webSocketMap.keySet()) {
            webSocketMap.get(i).sendMessage("在线人数：" + getOnlineCount());
        }
        logger.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    private int getOnlineCount() {
        return onlineCount.get();
    }


}
