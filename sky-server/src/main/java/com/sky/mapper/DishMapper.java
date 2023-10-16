package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品表数据库
 *
 * @author zengzhicheng
 */
@Mapper
public interface DishMapper {

    /**
     * 数据库插入新增的菜品信息
     *
     * @param dish 菜品
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);
}
