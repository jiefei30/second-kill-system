# 秒杀系统

>  基于springboot优雅设计一个秒杀系统乐观锁解决超卖、Redis缓存、令牌桶桶限流等方案（学习demo）

## 令牌桶

```java
//创建令牌桶实例
    private RateLimiter rateLimiter = RateLimiter.create(30);
//...

//1.没有获取到token请求一直直到获取到token令牌
            //logger.info("等待时间 : " + rateLimiter.acquire());   //会一直等待
            //2.设置一个等待时间，如果在等待的时间内获得到了token令牌，则处理业务，如果在等待时间内没有获取到token则抛弃
            if(!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)){
                ajax.setData("失败 !当前请求被限流，直接被抛弃");
                return ajax;
            }
```



## 乐观锁

```java
public Integer kill(Integer id) {
        Stock stock = stockService.getById(id);
        if(stock.getSale().equals(stock.getCount())){
            logger.info("库存不足");
            throw new RuntimeException("库存不足");
        }else {
            //库存
            LambdaUpdateWrapper<Stock> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.setSql("sale = (sale + 1)");
            updateWrapper.setSql("version = (version + 1)");
            updateWrapper.eq(Stock::getId,stock.getId())
                    .eq(Stock::getVersion,stock.getVersion());
            boolean res = stockService.update(updateWrapper);
            if(!res){
                throw new RuntimeException("抢购失败");
            }
            //创建订单
            StockOrder stockOrder = new StockOrder();
            stockOrder.setSid(stock.getId()).setName(stock.getName()).setCreateTime(new Date());
            stockOrderService.save(stockOrder);
            return stockOrder.getId();
        }
    }
```

## redis限时

```java
 public AjaxResponse time(Integer id){
        AjaxResponse ajax = AjaxResponse.newSuccess();
        try {

                logger.info("time : " + id);
                if(!stringRedisTemplate.hasKey("kill" + id)){
                    throw new RuntimeException("当前商品的请购活动已经结束");
                }
                ajax.setData("SUCCESS! ");
                return ajax;

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResponse.newInstance(500,e.getMessage());
        }
    }
```

