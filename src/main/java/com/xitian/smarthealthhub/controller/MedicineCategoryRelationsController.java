package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoryRelationsDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoryRelationsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoryRelationsVo;
import com.xitian.smarthealthhub.service.MedicineCategoryRelationsService;
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
 * 药品分类关联控制器
 * 
 * @author 
 * @date 2025/02/04
 */
@Slf4j
@Tag(name = "药品分类关联")
@RestController
@RequestMapping("/medicine-category-relations")
public class MedicineCategoryRelationsController {
    
    @Autowired
    private MedicineCategoryRelationsService medicineCategoryRelationsService;

    /**
     * 分页查询
     * 
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询")
    public ResultBean<PageBean<MedicineCategoryRelationsVo>> pageQuery(@RequestBody PageParam<MedicineCategoryRelationsQuery> param) {
        PageBean<MedicineCategoryRelationsVo> result = medicineCategoryRelationsService.pageQuery(param);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询
     * 
     * @param id ID
     * @return 实体
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public ResultBean<MedicineCategoryRelationsVo> get(@PathVariable Long id) {
        MedicineCategoryRelationsVo result = medicineCategoryRelationsService.get(id);
        return ResultBean.success(result);
    }

    /**
     * 新增
     * 
     * @param dto DTO
     * @return ID
     */
    @PostMapping
    @Operation(summary = "新增")
    public ResultBean<Long> add(@Valid @RequestBody MedicineCategoryRelationsDto dto) {
        Long result = medicineCategoryRelationsService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 修改
     * 
     * @param dto DTO
     * @return 是否成功
     */
    @PutMapping
    @Operation(summary = "修改")
    public ResultBean<Boolean> update(@Valid @RequestBody MedicineCategoryRelationsDto dto) {
        Boolean result = medicineCategoryRelationsService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicineCategoryRelationsService.delete(id);
        return ResultBean.success(result);
    }
}
