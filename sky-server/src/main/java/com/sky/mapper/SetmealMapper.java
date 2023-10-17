package com.sky.mapper;

import com.sky.annotation.AutoFill;
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
}
