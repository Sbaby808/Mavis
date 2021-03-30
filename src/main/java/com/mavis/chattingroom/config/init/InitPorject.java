package com.mavis.chattingroom.config.init;

import com.mavis.chattingroom.constants.AppParamRedisKeys;
import com.mavis.chattingroom.utils.jedis.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * @Author Sbaby
 * @Date 2021/3/6 15:30
 * @Version 1.0
 */
@Slf4j
@Component
public class InitPorject implements CommandLineRunner {

    @Autowired
    private WebSocketClient webSocketClient;
    @Autowired
    JedisUtils jedisUtils;
    @Resource
    private InitDao initDao;

    @Override
    public void run(String... args) throws Exception {
        // 加载系统参数至redis
        loadParameters();
        // 初始化系统websocket
        webSocketClient.connect();
    }

    /**
     * 加载系统配置参数至redis
     */
    private void loadParameters() {
        Field[] paramKeys = AppParamRedisKeys.class.getDeclaredFields();
        for(Field field : paramKeys) {
            if(!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                String value = field.get(AppParamRedisKeys.class).toString();
                String key = value.split(":")[3];
                jedisUtils.set(value, initDao.getAppParams(key));
            } catch (IllegalAccessException e) {
                log.error("反射获取系统参数key异常！");
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                log.error("获取系统参数key越界异常！");
                e.printStackTrace();
            }
        }
    }
}
