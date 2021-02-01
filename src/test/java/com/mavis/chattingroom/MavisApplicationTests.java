package com.mavis.chattingroom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class MavisApplicationTests {

    @Test
    void contextLoads() {
        BigDecimal b = new BigDecimal("123");
        String r = b.setScale(10).toString();
        System.out.println(r);
    }

}
