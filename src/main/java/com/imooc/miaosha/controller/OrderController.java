package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/16 7:52
 */
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    // @NeedLogin TODO 拦截器实现user为空判断
    public Result<OrderDetailVo> detail(User user, @RequestParam("orderId") Long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}
