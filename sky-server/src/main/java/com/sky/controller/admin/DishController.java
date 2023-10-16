package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品管理
 *
 * @author zengzhicheng
 */
@Slf4j
@Api(tags = "菜品相关接口")
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dto 新增菜品信息
     * @return 返回响应
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result<String> save(@RequestBody DishDTO dto) {
        log.info("新增菜品: {}", dto);
        dishService.save(dto);

        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 分页参数
     * @return 查询结果
     */
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

}
