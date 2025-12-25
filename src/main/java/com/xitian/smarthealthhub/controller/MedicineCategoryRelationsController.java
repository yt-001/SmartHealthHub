package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoryRelationsDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoryRelationsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoryRelationsVo;
import com.xitian.smarthealthhub.service.MedicineCategoryRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 药品与分类的关联表控制器
 */
@RestController
@RequestMapping("/medicine-category-relations")
public class MedicineCategoryRelationsController {

    @Autowired
    private MedicineCategoryRelationsService medicineCategoryRelationsService;

    /**
     * 分页查询药品与分类的关联信息
     */
    @GetMapping("/page")
    public ResultBean<PageBean<MedicineCategoryRelationsVo>> pageQuery(MedicineCategoryRelationsQuery query) {
        PageBean<MedicineCategoryRelationsVo> result = medicineCategoryRelationsService.pageQuery(query);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询药品与分类的关联信息
     */
    @GetMapping("/{id}")
    public ResultBean<MedicineCategoryRelationsVo> getById(@PathVariable Long id) {
        MedicineCategoryRelationsVo result = medicineCategoryRelationsService.getById(id);
        return ResultBean.success(result);
    }

    /**
     * 新增药品与分类的关联信息
     */
    @PostMapping("/create")
    public ResultBean<Long> add(@RequestBody MedicineCategoryRelationsDto dto) {
        Long result = medicineCategoryRelationsService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 更新药品与分类的关联信息
     */
    @PutMapping("/update")
    public ResultBean<Boolean> update(@RequestBody MedicineCategoryRelationsDto dto) {
        Boolean result = medicineCategoryRelationsService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除药品与分类的关联信息
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicineCategoryRelationsService.delete(id);
        return ResultBean.success(result);
    }
}