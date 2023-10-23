package com.sky.service;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

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

    /**
     * 查看历史订单
     *
     * @param ordersPageQueryDTO 请求参数
     * @return 返回分页历史订单数据
     */
    PageResult historyPage(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查询订单详情
     *
     * @param id 订单id
     * @return 返回订单数据
     */
    OrderVO details(Long id);

    /**
     * 用户取消订单业务接口
     *
     * @param id 订单id
     */
    void userCancelById(Long id);
}
