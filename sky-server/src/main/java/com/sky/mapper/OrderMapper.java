package com.sky.mapper;


import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

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
}
