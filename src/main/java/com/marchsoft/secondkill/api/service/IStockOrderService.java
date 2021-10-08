package com.marchsoft.secondkill.api.service;

import com.marchsoft.secondkill.api.entity.StockOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wangmingcan
 * @since 2020-08-09
 */
public interface IStockOrderService extends IService<StockOrder> {

    Integer kill(Integer id);

}
