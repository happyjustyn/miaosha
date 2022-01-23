package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.Goods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品及秒杀信息
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/6 19:20
 */
@Data
public class GoodsVo extends Goods {
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
}
