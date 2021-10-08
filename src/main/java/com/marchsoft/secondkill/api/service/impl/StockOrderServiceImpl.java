package com.marchsoft.secondkill.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.marchsoft.secondkill.api.controller.StockController;
import com.marchsoft.secondkill.api.entity.Stock;
import com.marchsoft.secondkill.api.entity.StockOrder;
import com.marchsoft.secondkill.api.mapper.StockOrderMapper;
import com.marchsoft.secondkill.api.service.IStockOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marchsoft.secondkill.api.service.IStockService;
import com.marchsoft.secondkill.vo.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wangmingcan
 * @since 2020-08-09
 */
//@Transactional
@Service
public class StockOrderServiceImpl extends ServiceImpl<StockOrderMapper, StockOrder> implements IStockOrderService {


    private final static Logger logger = LoggerFactory.getLogger(StockOrderServiceImpl.class);

    @Autowired
    IStockService stockService;

    @Autowired
    private IStockOrderService stockOrderService;
//
//    @Override
//    public Integer kill(Integer id) {
//        Stock stock = stockService.getById(id);
//        if(stock.getSale().equals(stock.getCount())){
//            logger.info("库存不足");
//            throw new RuntimeException("库存不足");
//        }else {
//            //库存
//            Stock newStock = new Stock();
//            newStock.setId(stock.getId()).setSale(stock.getSale()+1);
//            stockService.updateById(newStock);
//            //创建订单
//            StockOrder stockOrder = new StockOrder();
//            stockOrder.setSid(stock.getId()).setName(stock.getName()).setCreateTime(new Date());
//            stockOrderService.save(stockOrder);
//            return stockOrder.getId();
//        }
//    }

    @Override
    public Integer kill(Integer id) {
        Stock stock = stockService.getById(id);
        if(stock.getSale().equals(stock.getCount())){
            logger.info("库存不足");
            throw new RuntimeException("库存不足");
        }else {
            //库存
//            Stock newStock = new Stock();
            LambdaUpdateWrapper<Stock> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.setSql("sale = (sale + 1)");
            updateWrapper.setSql("version = (version + 1)");
            updateWrapper.eq(Stock::getId,stock.getId())
                    .eq(Stock::getVersion,stock.getVersion());
            boolean res = stockService.update(updateWrapper);
            if(!res){
                throw new RuntimeException("抢购失败");
            }
//            stockService.updateById(newStock);
            //创建订单
            StockOrder stockOrder = new StockOrder();
            stockOrder.setSid(stock.getId()).setName(stock.getName()).setCreateTime(new Date());
            stockOrderService.save(stockOrder);
            return stockOrder.getId();
        }
    }
}

