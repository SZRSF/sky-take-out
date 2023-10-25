package com.sky.service;


import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
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

    /**
     * 再来一单
     *
     * @param id 订单id
     */
    void repetition(Long id);

    /**
     * 订单搜索
     *
     * @param ordersPageQueryDTO 订单搜索参数
     * @return 返回搜索结果
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     *
     * @return 返回统计数据
     */
    OrderStatisticsVO statistics();

    /**
     * 接单
     *
     * @param ordersConfirmDTO 接单信息
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     *
     * @param ordersRejectionDTO 拒绝订单信息
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 取消订单
     *
     * @param ordersCancelDTO 取消订单信息
     */
    void cancel(OrdersCancelDTO ordersCancelDTO);

    /**
     * 派送订单
     *
     * @param id 订单id
     */
    void delivery(Long id);

    /**
     * 完成订单
     *
     * @param id 订单id
     */
    void complete(Long id);

    /**
     * 用户催单
     *
     * @param id 订单id
     */
    void reminder(Long id);
}
