package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/7 19:46
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;


    /**
     * 减库存，下订单，写入秒杀订单
     */
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        // 减库存
        goodsService.reduceStock(goods);
        // 下订单
        return orderService.createOrder(user, goods);
    }
}
