package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜品业务实现类
 *
 * @author zengzhicheng
 */
@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品
     *
     * @param dishDTO 新增菜品信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DishDTO dishDTO) {
        // 0.开启事物
        // 1.创建一个菜品对象
        Dish dish = new Dish();
        // 2.将dishDTO中的值赋给菜品对象
        BeanUtils.copyProperties(dishDTO, dish);
        // 3.将菜品信息存入数据库
        dishMapper.insert(dish);

        // 4.将菜品口味信息存入数据库
        // 4.1获取插入菜品的id
        Long dishId = dish.getId();
        // 4.2获取前端传来的口味列表
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 4.3判断口味列表是否为空,
        if (flavors != null && flavors .size()> 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            // 4.3插入多条口味信息到数据库
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 菜品分页查询业务实现
     *
     * @param dishPageQueryDTO 分页参数
     * @return 返回插叙结果
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        // 1.设置分页插件参数
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        // 2.查询菜品数据
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        // 3.返回查询结果
        return new PageResult(page.getTotal(), page.getResult());
    }
}
