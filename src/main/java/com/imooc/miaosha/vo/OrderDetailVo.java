package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.OrderInfo;
import lombok.Data;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/16 7:54
 */
@Data
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
}
