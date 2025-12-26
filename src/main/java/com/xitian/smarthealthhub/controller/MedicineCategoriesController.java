package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoriesDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 药品分类控制器
 * 
 * @author 
 * @date 2025/02/04
 */
@RestController
@RequestMapping("/medicine-categories")
@Tag(name = "药品分类管理")
@Slf4j
public class MedicineCategoriesController {

    @Autowired
    private MedicineCategoriesService medicineCategoriesService;

    /**
     * 分页查询药品分类
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询药品分类")
    public ResultBean<PageBean<MedicineCategoriesVo>> pageQuery(@RequestBody PageParam<MedicineCategoriesQuery> param) {
        PageBean<MedicineCategoriesVo> result = medicineCategoriesService.pageQuery(param);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询药品分类
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询药品分类")
    public ResultBean<MedicineCategoriesVo> getById(@PathVariable Long id) {
        MedicineCategoriesVo result = medicineCategoriesService.getById(id);
        return ResultBean.success(result);
    }

    /**
     * 新增药品分类
     */
    @PostMapping("/create")
    @Operation(summary = "新增药品分类")
    public ResultBean<Long> add(@RequestBody @Valid MedicineCategoriesDto dto) {
        Long result = medicineCategoriesService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 更新药品分类
     */
    @PutMapping("/update")
    @Operation(summary = "更新药品分类")
    public ResultBean<Boolean> update(@RequestBody @Valid MedicineCategoriesDto dto) {
        Boolean result = medicineCategoriesService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除药品分类
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除药品分类")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicineCategoriesService.delete(id);
        return ResultBean.success(result);
    }

    /**
     * 获取所有大类（一级分类）列表
     *
     * @return 大类分类列表
     */
    @GetMapping("/big-list")
    @Operation(summary = "获取药品大类列表")
    public ResultBean<java.util.List<MedicineCategoriesVo>> listBigCategories() {
        java.util.List<MedicineCategoriesVo> result = medicineCategoriesService.listBigCategories();
        return ResultBean.success(result);
    }

    /**
     * 根据大类ID获取小类列表
     *
     * @param parentId 父分类ID
     * @return 小类分类列表
     */
    @GetMapping("/sub-list/{parentId}")
    @Operation(summary = "根据大类ID获取小类列表")
    public ResultBean<java.util.List<MedicineCategoriesVo>> listSubCategories(@PathVariable Long parentId) {
        java.util.List<MedicineCategoriesVo> result = medicineCategoriesService.listSubCategoriesByParentId(parentId);
        return ResultBean.success(result);
    }
}
