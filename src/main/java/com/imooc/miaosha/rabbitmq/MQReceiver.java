package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/2/7 11:36
 */
@Slf4j
@Service
public class MQReceiver {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(@Payload MiaoshaMessage message) {
        log.info("receive msg:" + message);
        MiaoshaUser user=message.getUser();
        long goodsId = message.getGoodsId();

        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        // 判断是否已经秒杀过了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        // 进行秒杀
        // 减库存，下订单，写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive msg:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void topicReceive1(String message) {
        log.info("topicReceive1 msg:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void topicReceive2(String message) {
        log.info("topicReceive2 msg:" + message);
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void headersReceive1(String message) {
        log.info("headersReceive msg:" + message);
    }
}
