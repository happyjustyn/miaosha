package com.imooc.miaosha.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * order_info
 * @author 
 */
@Data
public class OrderInfo implements Serializable {
    /**
     * 订单id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 收货地址
     */
    private Long deliveryAddrId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品数量
     */
    private Integer goodsCount;

    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 渠道,1-pc,2-android,3-ios
     */
    private Integer orderChannel;

    /**
     * 订单状态,0-新建未支付,1-已支付,2-已发货,3-已收货,4-已退款,5-已完成
     */
    private Integer status;

    /**
     * 创建订单时间
     */
    private Date createDate;

    /**
     * 支付时间
     */
    private Date payDate;

    private static final long serialVersionUID = 1L;
}