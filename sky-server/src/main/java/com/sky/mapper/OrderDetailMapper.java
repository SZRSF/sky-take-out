package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单接口实现
 *
 * @author zengzhicheng
 */
@Mapper
public interface OrderDetailMapper {

    /**
     * 批量插入订单明细数据
     *
     * @param orderDetails
     */
    void insertBatch(List<OrderDetail> orderDetails);
}
