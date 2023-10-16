package com.sky.mapper;


import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 口味数据库操作
 *
 * @author zengzhicheng
 */
@Mapper
public interface DishFlavorMapper {

    /**
     * 插入多条口味到数据库
     *
     * @param flavors 多条口味列表
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品Id查找口味
     *
     * @param id 要查找菜品的Id
     * @return 返回查询的结果
     */
    List<DishFlavor> getByDishId(Long id);

    /**
     * 删掉菜品的口味
     *
     * @param id 菜品Id
     */
    void deleteByDishId(Long id);
}
