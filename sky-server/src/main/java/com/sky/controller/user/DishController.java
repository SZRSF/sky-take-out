package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端-菜品浏览接口
 *
 * @author zengzhicheng
 */
@Slf4j
@Api(tags = "C端-菜品浏览接口")
@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类Id
     * @return 返回响应
     */
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        // 查询起售中的菜品
        dish.setStatus(StatusConstant.ENABLE);

        List<DishVO> list = dishService.listWithFlavor(dish);

        return Result.success(list);
    }
}
