package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据订单id查询订单
     *
     * @param id 订单id
     * @return 返回查询结果
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 根据状态统计订单数量
     *
     * @param status 状态
     * @return 返回统计数量
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 根据状态和下单时间查询订单
     *
     * @param status 状态
     * @param orderTime 下单时间
     * @return 返回订单
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLt(Integer status, LocalDateTime orderTime);

    /**
     * 根据动态条件统计营业额
     *
     * @param map
     * @return
     */
    Double sumByMap(Map<String, Object> map);

    /**
     *根据动态条件统计订单数量
     *
     * @param map
     * @return
     */
    Integer countByMap(Map<String, Object> map);

    /**
     * 查询商品销量排名
     *
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
