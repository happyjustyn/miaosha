# Java秒杀系统方案优化-高性能高并发实战

### 项目结构

![](https://cdn.jsdelivr.net/gh/HappyJustyn/cloudimg/img/20211231200148.png)

### 使用技术

![](https://cdn.jsdelivr.net/gh/HappyJustyn/cloudimg/img/20211231195857.png)

### 重点模块及功能

#### 1. 项目框架搭建

- Result封装返回结果集
  
- 集成redis
  
  - 使用jedis自定义redis读写工具类
    
  - 实现key封装类，避免各模块key重复
    

#### 2. 登录功能实现

- 两次md5加密
  
  - 前端加密密码传输到后台（明文+固定salt）
    
  - 后端再进行一次加密（前台数据+随机salt）
    
- jsr303参数校验
  
- 全局异常处理器+自定义业务异常
  
- redis实现分布式session
  

#### 3. 商品页面及秒杀功能实现

#### 4. 初次JMeter压测

- top命令监控linux服务器资源占用情况，得知系统瓶颈在于mysql数据库
  
- linux上使用JMeter步骤：
  
  1. 在windows上录好jmx文件
    
  2. 上传jmx文件到linux，运行命令行：`sh jmeter.sh -n -t XXX.jmx -l result.jtl`
    
  3. 下载result.jtl文件到windows，导入jmeter查看
    
- 在linux上运行java项目：`nohup java -jar XXX.jar &`
  
- 出现了秒杀库存为负的情况（超卖问题）
  

#### 5. 页面优化

- 页面缓存+对象缓存
  
  - 页面缓存有效期较短，例如用于缓存商品列表前几页的数据
  - 对象缓存有效期较长，例如用于缓存用户信息，注意对象更新时，及时处理缓存
- 页面静态化，前后端分离
  
  - 利用浏览器的缓存
- 解决超卖问题
  
  - 减库存的sql中，where语句加上**stock_count > 0**
- 避免重复秒杀
  
  - 秒杀订单加唯一索引：`UNIQUE KEY u_uid_gId (user_id,goods_id) USING BTREE`
- 静态资源优化
  
  - JS/CSS压缩，减少流量（webpack）
    
  - 多个JS/CSS组合，减少连接数（tengine）
    
  - CDN就近访问
    

#### 6. 接口优化

- Redis预减库存减少数据库访问
  
- 内存标记减少Redis访问
  
- 请求先入队缓冲，异步下单，增强用户体验
  
- 分库分表（mycat）
  

##### 思路：减少数据库访问

1. 系统初始化，把商品库存数量加载到Redis
  
2. 受到请求，Redis预减库存，库存不足，直接返回，否则进入3
  
3. 请求入队，立即返回排队中
  
4. 请求出队，生成订单，减少库存
  
5. 客户端轮询，是否秒杀成功
  

##### 集成rabbitmq

- rabbitmq的四种交换机模式
  
  - direct模式
    
  - topic模式
    
  - fanout模式
    
  - headers模式
    

##### nginx对应用进行水平扩展

- 反向代理
  
- 负载均衡
  

##### 压测前准备

- 执行`UserUtil`类的`createUser`方法，以创表`miaosha_user`的数据，和模拟登录以写入token到缓存（写入数据库的操作执行一次就可以，写入到缓存的token可能过期，token过期需要重新写入）
  
- 删除`miaosha_order`和`order_info`两张表的数据
  
- 修改`miaosha_goods`表的商品数量
  
- 重启系统，让缓存加载秒杀商品数量
  

#### 7. 安全优化

- 秒杀接口地址隐藏
  
  - 防止恶意刷单
- 数学公式验证码
  
  - 防止机器人自动请求
    
  - 分散用户请求
    
- 接口限流防刷
  
  - 利用缓存限制用户对某接口的访问次数
- 自定义注解配合拦截器的使用
  
- `ThreadLocal`的使用
