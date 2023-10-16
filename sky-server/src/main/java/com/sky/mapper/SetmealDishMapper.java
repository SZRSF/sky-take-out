package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品套餐关联表
 * @author zengzhicheng
 */
@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品Id查询对应的套餐id
     *
     * @param ids 菜品Id
     * @return 返回结果
     */
    List<Long> getSetmealIdsByDishId(List<Long> ids);
}
