package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/2/7 19:28
 */
@Data
public class MiaoshaMessage implements Serializable {
    private MiaoshaUser user;
    private long goodsId;
}
