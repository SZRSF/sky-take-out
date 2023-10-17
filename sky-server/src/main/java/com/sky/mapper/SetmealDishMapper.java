package com.sky.mapper;

import com.sky.entity.SetmealDish;
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

    /**
     * 批量插进套餐菜品数据
     *
     * @param setmealDishList 套餐和菜品关联的集合
     */
    void insertBatch(List<SetmealDish> setmealDishList);


    /**
     * 根据套餐id查询关联的菜品
     * @param id 套餐Id
     * @return 返回结果
     */
    List<SetmealDish> getByDishId(Long id);
}
