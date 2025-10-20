package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.converter.DepartmentsConverter;
import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.DepartmentsQuery;
import com.xitian.smarthealthhub.domain.vo.DepartmentsVO;
import com.xitian.smarthealthhub.service.DepartmentsService;
import com.xitian.smarthealthhub.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 科室控制器
 */
@Tag(name = "DepartmentsController", description = "科室管理接口")
@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Resource
    private DepartmentsService departmentsService;

    @Resource
    private UsersService usersService;

    /**
     * 查询科室列表
     * @param param 分页参数和查询条件
     * @return 科室列表
     */
    @Operation(summary = "查询科室列表")
    @PostMapping("/list")
    public ResultBean<PageBean<DepartmentsVO>> list(@RequestBody PageParam<DepartmentsQuery> param) {
        return null;
    }
}