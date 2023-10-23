package com.sky.service;


import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderPaymentVO;
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

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 订单支付信息
     * @return  返回支付成功信息
     */
    OrderPaymentVO payment (OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo 订单号
     */
    void paySuccess(String outTradeNo);
}
