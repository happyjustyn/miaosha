package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/2 22:15
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;

    /**
     * QPS:123
     * 5000*2
     */
    @PostMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderInfo> toList(Model model, MiaoshaUser user, @RequestParam("goodsId") Long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        // 判断是否已经秒杀过了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAOSHA);
        }

        // 进行秒杀
        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);
    }

}
