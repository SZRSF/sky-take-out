package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

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
    DishVO getById(Long id);

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品的新增数据
     */
    void update(DishDTO dishDTO);

    /**
     * 批量删除菜品
     *
     * @param ids 菜品Id
     */
    void delete(List<Long> ids);

    /**
     * 菜品起售、停止状态
     *
     * @param id 菜品id
     * @param status 菜品更改后的状态
     */
    void startOrStop(Long id, Integer status);

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类Id
     * @return 返回菜品信息
     */
    List<DishVO> getByCategoryId(Long categoryId, String name);

    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
