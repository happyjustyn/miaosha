package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MQSenderTest {

    @Autowired
    MQSender mqSender;

    @Test
    void send() {
        mqSender.send("hello,imooc");
    }

    @Test
    void topicSend() {
        mqSender.topicSend("你好世界");
    }


    @Test
    void fanoutSend() {
        mqSender.fanoutSend("你好java");
    }

    @Test
    void headersSend() {
        mqSender.headersSend("你好中国！");
    }

    @Test
    void sendMiaoshaMessage() {
        MiaoshaMessage message = new MiaoshaMessage();
        MiaoshaUser user = new MiaoshaUser();
        user.setNickname("testuser");
        message.setUser(user);
        message.setGoodsId(10000L);
        mqSender.sendMiaoshaMessage(message);
    }
}