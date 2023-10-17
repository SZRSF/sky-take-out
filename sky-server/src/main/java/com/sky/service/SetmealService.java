package com.sky.service;

import com.sky.dto.SetmealDTO;

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
}
