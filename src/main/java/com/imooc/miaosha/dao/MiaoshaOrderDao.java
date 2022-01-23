package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface MiaoshaOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaOrder record);

    int insertSelective(MiaoshaOrder record);

    MiaoshaOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoshaOrder record);

    int updateByPrimaryKey(MiaoshaOrder record);
}