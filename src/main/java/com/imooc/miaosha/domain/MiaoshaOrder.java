package com.imooc.miaosha.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * miaosha_order
 * @author 
 */
@Data
public class MiaoshaOrder implements Serializable {
    /**
     * 订单id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long goodsId;

    private static final long serialVersionUID = 1L;
}