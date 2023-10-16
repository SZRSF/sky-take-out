package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
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

    @Autowired
    private SetmealDishMapper setmealDishMapper;

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
        if (flavors != null && flavors.size() > 0) {
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

    /**
     * 根据id查询菜品
     *
     * @param id 要查寻菜品的id
     * @return 返回查询的结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DishVO getById(Long id) {
        // 1.根据id查询菜品
        Dish dish = dishMapper.getById(id);

        // 2.根据菜品id查询口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);

        // 3.拼接返回数据
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品的新增数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DishDTO dishDTO) {
        // 1.获取需要更新的Dish对象
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 2.更新菜品
        dishMapper.update(dish);

        // 3.删除原有的口味数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        // 4.获取菜品需要的口味
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        // 4.更新口味
        if (dishFlavors != null && dishFlavors.size() > 0) {
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            // 批量更新口味
            dishFlavorMapper.insertBatch(dishFlavors);
        }

    }

    /**
     * 批量删除菜品
     *
     * @param ids 菜品Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        // 1.判断当前菜品是否能够删除
        // 1.1是否存在起售中的菜品
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)) {
                // 当前菜品处于起售中，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 1.2是否被套餐关联
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishId(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            // 当前菜品被套餐关联了
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 2.删除菜品
        for (Long id : ids) {
            // 2.1 删除菜品表里的数据
            dishMapper.deleteById(id);
            //2.2 删除与菜品相关的口味
            dishFlavorMapper.deleteByDishId(id);
        }
    }
}
