package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

/**
 * 套餐业务接口
 *
 * @author zengzhicheng
 */
public interface SetmealService {

    /**
     * 新增套餐业务接口
     *
     * @param setmealDTO 套餐信息
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询业务接口
     *
     * @param setmealPageQueryDTO 套餐分页查询参数
     * @return 返回分页查询结果
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
