package com.imooc.miaosha.dao;

import com.imooc.miaosha.vo.GoodsVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoodsDaoTest {

    @Autowired
    GoodsDao goodsDao;

    @Test
    void listGoodsVo() {
        List<GoodsVo> list=goodsDao.listGoodsVo();
        System.out.println(list);
    }
}