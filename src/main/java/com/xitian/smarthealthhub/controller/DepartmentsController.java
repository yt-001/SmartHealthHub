//package com.xitian.smarthealthhub.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.xitian.smarthealthhub.bean.PageBean;
//import com.xitian.smarthealthhub.bean.PageParam;
//import com.xitian.smarthealthhub.bean.ResultBean;
//import com.xitian.smarthealthhub.domain.entity.Departments;
//import com.xitian.smarthealthhub.service.DepartmentsService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 科室Controller
// */
//@RestController
//@RequestMapping("/departments")
//@Tag(name = "科室管理", description = "科室相关接口")
//public class DepartmentsController {
//
//    @Autowired
//    private DepartmentsService departmentsService;
//
//    /**
//     * 分页查询科室列表
//     *
//     * @param pageParam 分页参数
//     * @return 科室分页数据
//     */
//    @Operation(summary = "分页查询科室列表")
//    @GetMapping("/page")
//    public ResultBean<PageBean<Departments>> pageDepartments(PageParam pageParam) {
//        // 分页查询
//        Page<Departments> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
//        LambdaQueryWrapper<Departments> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByAsc(Departments::getName);
//
//        departmentsService.page(page, queryWrapper);
//
//        // 封装结果
//        PageBean<Departments> pageBean = new PageBean<>();
//        pageBean.setTotal(page.getTotal());
//        pageBean.setRecords(page.getRecords());
//
//        return ResultBean.success(pageBean);
//    }
//
//    /**
//     * 查询所有启用的科室
//     *
//     * @return 科室列表
//     */
//    @Operation(summary = "查询所有启用的科室")
//    @GetMapping
//    public ResultBean<List<Departments>> listActiveDepartments() {
//        LambdaQueryWrapper<Departments> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Departments::getIsActive, (byte) 1);
//        queryWrapper.orderByAsc(Departments::getName);
//
//        List<Departments> departments = departmentsService.list(queryWrapper);
//        return ResultBean.success(departments);
//    }
//
//    /**
//     * 根据ID查询科室详情
//     *
//     * @param id 科室ID
//     * @return 科室详情
//     */
//    @Operation(summary = "根据ID查询科室详情")
//    @GetMapping("/{id}")
//    public ResultBean<Departments> getDepartmentById(@PathVariable Long id) {
//        Departments department = departmentsService.getById(id);
//        if (department == null) {
//            return ResultBean.fail(404, "科室不存在");
//        }
//        return ResultBean.success(department);
//    }
//
//    /**
//     * 新增科室
//     *
//     * @param department 科室信息
//     * @return 操作结果
//     */
//    @Operation(summary = "新增科室")
//    @PostMapping
//    public ResultBean<String> addDepartment(@RequestBody Departments department) {
//        // 检查科室名称是否已存在
//        LambdaQueryWrapper<Departments> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Departments::getName, department.getName());
//        if (departmentsService.count(queryWrapper) > 0) {
//            return ResultBean.fail(400, "科室名称已存在");
//        }
//
//        // 设置默认状态为启用
//        department.setIsActive((byte) 1);
//
//        // 保存科室信息
//        boolean saved = departmentsService.save(department);
//        if (saved) {
//            return ResultBean.success("科室添加成功");
//        } else {
//            return ResultBean.fail(500, "科室添加失败");
//        }
//    }
//
//    /**
//     * 更新科室信息
//     *
//     * @param id 科室ID
//     * @param department 科室信息
//     * @return 操作结果
//     */
//    @Operation(summary = "更新科室信息")
//    @PutMapping("/{id}")
//    public ResultBean<String> updateDepartment(@PathVariable Long id, @RequestBody Departments department) {
//        department.setId(id);
//        boolean updated = departmentsService.updateById(department);
//        if (updated) {
//            return ResultBean.success("科室信息更新成功");
//        } else {
//            return ResultBean.fail(500, "科室信息更新失败");
//        }
//    }
//
//    /**
//     * 删除科室
//     *
//     * @param id 科室ID
//     * @return 操作结果
//     */
//    @Operation(summary = "删除科室")
//    @DeleteMapping("/{id}")
//    public ResultBean<String> deleteDepartment(@PathVariable Long id) {
//        boolean removed = departmentsService.removeById(id);
//        if (removed) {
//            return ResultBean.success("科室删除成功");
//        } else {
//            return ResultBean.fail(500, "科室删除失败");
//        }
//    }
//}