package com.sky.service;


import com.sky.dto.DishDTO;

/**
 * 菜品模块业务实现接口
 *
 * @author zengzhicheng
 */
public interface DishService {

    /**
     * 新增菜品
     *
     * @param dishDTO 新增菜品信息
     */
    void save(DishDTO dishDTO);
}
