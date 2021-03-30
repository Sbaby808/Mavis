package com.mavis.chattingroom.config.websocket;

/**
 * @Author Sbaby
 * @Date 2021/3/6 11:43
 * @Version 1.0
 */
public interface WebSocketClientService {

    /**
     * 群发
     * @param message
     */
    void groupSending(Object message);

    /**
     * 指定发送
     * @param topic
     * @param message
     */
    void appointSending(String topic,Object message);
}
