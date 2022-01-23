package com.imooc.miaosha.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * goods
 */
@Data
public class Goods implements Serializable {
    /**
     * 商品id
     */
    private Long id;

    /**
     * 名称
     */
    private String goodsName;

    /**
     * 标题
     */
    private String goodsTitle;

    /**
     * 图片
     */
    private String goodsImg;

    /**
     * 详情
     */
    private String goodsDetail;

    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 库存,-1表示没有限制
     */
    private Integer goodsStock;

    private static final long serialVersionUID = 1L;
}