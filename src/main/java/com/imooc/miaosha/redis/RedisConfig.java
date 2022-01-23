package com.imooc.miaosha.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取redis配置
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/31 7:59
 */
@Component
@ConfigurationProperties(prefix = "redis") // 该前缀下的属性，会自动绑定类的属性
@Getter
@Setter
public class RedisConfig {
    private String host;
    private int port;
    private String password;
    private int timeout;
    private int database;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;
}
