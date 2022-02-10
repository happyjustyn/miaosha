package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.dao.MiaoshaGoodsDao;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/6 19:18
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;
    @Autowired
    MiaoshaGoodsDao miaoshaGoodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * 减库存
     */
    public boolean reduceStock(GoodsVo goods) {
        int count = miaoshaGoodsDao.reduceStock(goods.getId());
        return count > 0;
    }
}
