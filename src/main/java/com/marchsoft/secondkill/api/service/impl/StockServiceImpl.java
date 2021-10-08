package com.marchsoft.secondkill.api.service.impl;

import com.marchsoft.secondkill.api.entity.Stock;
import com.marchsoft.secondkill.api.mapper.StockMapper;
import com.marchsoft.secondkill.api.service.IStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wangmingcan
 * @since 2020-08-09
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

}
