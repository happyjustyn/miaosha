package com.imooc.miaosha.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/2/7 11:33
 */
@Slf4j
@Service
public class MQSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMiaoshaMessage(MiaoshaMessage message) {
        log.info("send msg:" + message);
        rabbitTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, message);
    }

    public void send(Object message) {
        log.info("send msg:" + message);
        rabbitTemplate.convertAndSend(MQConfig.QUEUE, message);
    }

    public void topicSend(Object message) {
        log.info("topic-send msg:" + message);
        rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.ROUTING_KEY1, "1-" + message);
        rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.ROUTING_KEY2, "2-" + message);
    }

    public void fanoutSend(Object message) {
        log.info("fanout-send msg:" + message);
        rabbitTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", message);
    }

    private final static MessagePostProcessor messagePostProcessor = message -> {
        MessageProperties properties = message.getMessageProperties();
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");
        return message;
    };

    public void headersSend(Object message) {
        log.info("headers-send msg:" + message);
        rabbitTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", message, messagePostProcessor);
    }

}
