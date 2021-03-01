package com.mavis.chattingroom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{name}")
public class WebSocket {

    private static Logger logger = LoggerFactory.getLogger(WebSocket.class);

    private Session session;
    private String name;
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "name") String name) {
        this.session = session;
        this.name = name;
        webSocketSet.put(name, this);
        logger.info("[WebSocket] 连接成功，当前连接人数为：={}", webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this.name);
        logger.info("[WebSocket] 退出成功，当前连接人数为：={}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("[WebSocket] 收到消息：={}", message);
        if(message.indexOf("TOUSER") == 0) {
            String name = message.substring(message.indexOf("TOUSER") + 6, message.indexOf(";"));
            appointSending(name, message.substring(message.indexOf(";") + 1, message.length()));
        } else {
            message = this.name + ":" + message;
            groupSending(message);
        }
    }

    public void groupSending(String message) {
        for(String name : webSocketSet.keySet()) {
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void appointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
