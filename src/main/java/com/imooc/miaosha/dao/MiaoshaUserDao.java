package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaUser;
import org.springframework.stereotype.Repository;

@Repository
public interface MiaoshaUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaUser record);

    int insertSelective(MiaoshaUser record);

    MiaoshaUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoshaUser record);

    int updateByPrimaryKey(MiaoshaUser record);
}