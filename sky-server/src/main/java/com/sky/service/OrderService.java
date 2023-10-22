package com.sky.service;


import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

/**
 * 订单业务接口
 *
 * @author zengzhicheng
 */
public interface OrderService {

    /**
     * 用户下单业务实现接口
     *
     * @param ordersSubmitDTO 下单的信息
     * @return 返回下单成功的信息
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
