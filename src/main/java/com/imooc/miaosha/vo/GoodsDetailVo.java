package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.MiaoshaUser;
import lombok.Data;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/14 15:20
 */
@Data
public class GoodsDetailVo {
    // 还多久秒杀开始
    private int remainSeconds;
    // 秒杀状态判断
    private int miaoshaStatus;
    private GoodsVo goods;
    private MiaoshaUser user;
}
