package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品表数据库
 *
 * @author zengzhicheng
 */
@Mapper
public interface DishMapper {

    /**
     * 数据库插入新增的菜品信息
     *
     * @param dish 菜品
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询数据库操作
     *
     * @param dishPageQueryDTO 分页查询参数
     * @return 返回查询结果
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     *
     * @param id 菜品Id
     * @return 返回查询的菜品
     */
    Dish getById(Long id);

    /**
     * 更新菜品
     *
     * @param dish 更新的菜品数据
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 删除菜品
     *
     * @param id 要删除的菜品id
     */
    void deleteById(Long id);

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return 返回查询的结果
     */
    List<DishVO> getByCategoryId(Long categoryId);
}
