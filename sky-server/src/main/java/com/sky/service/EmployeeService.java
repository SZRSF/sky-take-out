package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

/**
 * @author zengzhicheng
 */
public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 前端登录请求数据
     * @return 返回数据库查询结果
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employee 员工的信息
     */
    void save(EmployeeDTO employee);

    /**
     * 员工分页查询
     *
     * @param pageQueryDTO 分页查询参数
     * @return 返回分页查询结果
     */
    PageResult pageQuery(EmployeePageQueryDTO pageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param status 状态，1为启用 0为禁用
     * @param id 员工id
     */
    void startOrStop(Integer status, Long id);
}
