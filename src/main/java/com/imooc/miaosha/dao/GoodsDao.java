package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDao {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    /**
     * 查询商品及秒杀信息
     */
    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(Long goodsId);
}