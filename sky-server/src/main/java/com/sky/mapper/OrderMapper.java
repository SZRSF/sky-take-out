package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 订单接口操作
 *
 * @author zengzhicheng
 */
@Mapper
public interface OrderMapper {

    /**
     * 向订单表插入数据
     *
     * @param order
     */
    void insert(Orders order);

    /**
     * 根据订单号和用户id查询订单
     *
     * @param outTradeNo 订单号
     * @param userId 用户id
     * @return 返回订单信息
     */
    @Select("select * from orders where number = #{outTradeNo} and user_id= #{userId}")
    Orders getByNumberAndUserId(String outTradeNo, Long userId);

    /**
     * 修改订单信息
     *
     * @param orders 修改的订单信息
     */
    void update(Orders orders);

    /**
     * 条件分页查询历史订单
     *
     * @param ordersPageQueryDTO 请求参数
     * @return 返回查询结果
     */
    Page<Orders> queryList(OrdersPageQueryDTO ordersPageQueryDTO);
}
