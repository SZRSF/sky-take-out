package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

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

    /**
     * 根据套餐Id查询
     *
     * @param id 套餐id
     * @return 返回查询结果
     */
    SetmealVO getById(Long id);

    /**
     * 修改套餐
     *
     * @param setmealVO 修改的套餐数据
     */
    void update(SetmealVO setmealVO);

    /**
     * 套餐起售、停售
     *
     * @param id 套餐Id
     * @param status 套餐状态
     */
    void startOrStop(Long id, Integer status);

    /**
     * 批量删除套餐
     *
     * @param ids 套餐id
     */
    void deleteBatch(List<Long> ids);
}
