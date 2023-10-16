package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 分页参数
     * @return 分页查询结果
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据Id查询菜品
     *
     * @param id 要查寻菜品的id
     * @return 返回查询的结果
     */
    DishVO getById(String id);
}
