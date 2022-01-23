package com.imooc.miaosha.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * miaosha_goods
 * @author 
 */
@Data
public class MiaoshaGoods implements Serializable {
    /**
     * 秒杀商品id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 秒杀价
     */
    private BigDecimal miaoshaPrice;

    /**
     * 库存,-1表示没有限制
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;

    private static final long serialVersionUID = 1L;
}