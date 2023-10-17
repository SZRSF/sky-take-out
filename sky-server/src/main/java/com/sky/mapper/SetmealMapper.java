package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 套餐表操作
 *
 * @author zengzhicheng
 */
@Mapper
public interface SetmealMapper {

    /**
     * 新增套餐
     *
     * @param setmeal 套餐信息
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 套餐分页查询参数
     * @return 返回分页查询结果
     */
    Page<Setmeal> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id找套餐
     *
     * @param id 套餐Id
     * @return 返回查询结果
     */
    Setmeal getById(Long id);

    /**
     * 更新套餐
     *
     * @param setmeal 更新的套餐数据
     */
    void update(Setmeal setmeal);
}
