package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.MiaoshaOrderDao;
import com.imooc.miaosha.dao.OrderInfoDao;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.OrderKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/7 19:23
 */
@Service
public class OrderService {

    @Autowired
    OrderInfoDao orderInfoDao;
    @Autowired
    MiaoshaOrderDao miaoshaOrderDao;
    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(Long id, Long goodsId) {
//         return orderInfoDao.getMiaoshaOrderByUserIdAndGoodsId(id, goodsId);
        // 从缓存取，不读取数据库
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, "" + id + "_" + goodsId, MiaoshaOrder.class);
    }

    /**
     * 下订单：商品订单和秒杀订单
     */
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        orderInfoDao.insertSelective(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrderDao.insertSelective(miaoshaOrder);

        // 秒杀订单信息写入缓存，方便判断是否已经秒杀到
        redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId() + "_" + goods.getId(), miaoshaOrder);

        return orderInfo;
    }

    public OrderInfo getOrderById(Long orderId) {
        return orderInfoDao.selectByPrimaryKey(orderId);
    }
}
