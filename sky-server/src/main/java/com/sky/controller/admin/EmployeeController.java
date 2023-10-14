package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 *
 * @author zengzhicheng
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api("员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO 接受前端登录请求数据
     * @return 返回登录结果
     */
    @ApiOperation("员工登录接口")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return 返回结果
     */
    @ApiOperation("员工退出登录接口")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param employee 员工的信息
     * @return 返回响应
     */
    @ApiOperation("新增员工接口")
    @PostMapping
    public Result<String> save(@RequestBody EmployeeDTO employee) {
        log.info("新增的员工数据：{}", employee);
        employeeService.save(employee);
        return Result.success();
    }

    /**
     * 员工分页查询
     *
     * @param pageQueryDTO 分页查询
     * @return 返回分页数据
     */
    @ApiOperation("员工分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO pageQueryDTO) {
        log.info("分页查询参数:{}", pageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用或禁用员工账号
     *
     * @param status 状态，1为启用 0为禁用
     * @param id     员工id
     * @return 响应数据
     */
    @ApiOperation("启用禁用员工账号")
    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号 status={}, id={}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 根据ud查询员工
     *
     * @param id 员工Id
     * @return 返回查询的结果
     */
    @ApiOperation("根据Id查询员工")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("要查询的员工id：{}", id);
        return Result.success(employeeService.getById(id));
    }

    /**
     * 编辑员工信息
     *
     * @param employeeDTO 更新员工信息
     * @return 响应数据
     */
    @ApiOperation("编辑员工信息")
    @PutMapping
    public Result<String> update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
