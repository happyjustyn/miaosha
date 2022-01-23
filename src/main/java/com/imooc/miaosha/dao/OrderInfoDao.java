package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("userId") Long id,@Param("goodsId") Long goodsId);
}