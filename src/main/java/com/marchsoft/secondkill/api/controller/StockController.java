package com.marchsoft.secondkill.api.controller;


import com.google.common.util.concurrent.RateLimiter;
import com.marchsoft.secondkill.api.entity.Stock;
import com.marchsoft.secondkill.api.entity.StockOrder;
import com.marchsoft.secondkill.api.service.IStockOrderService;
import com.marchsoft.secondkill.api.service.IStockService;
import com.marchsoft.secondkill.common.handler.MSExceptionHandler;
import com.marchsoft.secondkill.vo.AjaxResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wangmingcan
 * @since 2020-08-09
 */
@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final static Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private IStockOrderService stockOrderService;

    @Autowired
    IStockService stockService;

    //创建令牌桶实例
    private RateLimiter rateLimiter = RateLimiter.create(30);

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @GetMapping("/test")
    @ApiOperation("test")
    public AjaxResponse test(){
        AjaxResponse ajax = AjaxResponse.newSuccess();
        ajax.setData("SUCCESS!");
        return ajax;
    }

//    @GetMapping("/kill")
//    @ApiOperation("kill")
//    public AjaxResponse kill(Integer id){
//        AjaxResponse ajax = AjaxResponse.newSuccess();
//        try {
//            //悲观锁
//            synchronized (this){
//                logger.info("kill : " + id);
//                int orderId = stockOrderService.kill(id);
//                ajax.setData("SUCCESS! " + orderId);
//                return ajax;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return AjaxResponse.newInstance(500,e.getMessage());
//        }
//    }


    @GetMapping("/sale")
    @ApiOperation("sale")
    public AjaxResponse sale(Integer id){
        AjaxResponse ajax = AjaxResponse.newSuccess();
        try {
            //1.没有获取到token请求一直直到获取到token令牌
            //logger.info("等待时间 : " + rateLimiter.acquire());   //会一直等待
            //2.设置一个等待时间，如果在等待的时间内获得到了token令牌，则处理业务，如果在等待时间内没有获取到token则抛弃
            if(!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)){
                ajax.setData("失败 !当前请求被限流，直接被抛弃");
                return ajax;
            }
            try {

                logger.info("kill : " + id);
                int orderId = stockOrderService.kill(id);
                ajax.setData("SUCCESS! " + orderId);
                return ajax;

            }catch (Exception e){
                e.printStackTrace();
                return AjaxResponse.newInstance(500,e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResponse.newInstance(500,e.getMessage());
        }
    }

    /**
     * @author Wangmingcan
     * @date 2020-08-10 16:17
     * @param id
     * @return
     * @description  乐观锁
     */
    @GetMapping("/kill")
    @ApiOperation("kill")
    public AjaxResponse kill(Integer id){
        AjaxResponse ajax = AjaxResponse.newSuccess();
        try {

                logger.info("kill : " + id);
                int orderId = stockOrderService.kill(id);
                ajax.setData("SUCCESS! " + orderId);
                return ajax;

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResponse.newInstance(500,e.getMessage());
        }
    }

    @GetMapping("/time")
    @ApiOperation("time")
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
}

