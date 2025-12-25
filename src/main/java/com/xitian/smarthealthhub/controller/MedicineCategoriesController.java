package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoriesDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 药品分类控制器
 */
@RestController
@RequestMapping("/medicine-categories")
public class MedicineCategoriesController {

    @Autowired
    private MedicineCategoriesService medicineCategoriesService;

    /**
     * 分页查询药品分类
     */
    @GetMapping("/page")
    public ResultBean<PageBean<MedicineCategoriesVo>> pageQuery(MedicineCategoriesQuery query) {
        PageBean<MedicineCategoriesVo> result = medicineCategoriesService.pageQuery(query);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询药品分类
     */
    @GetMapping("/{id}")
    public ResultBean<MedicineCategoriesVo> getById(@PathVariable Long id) {
        MedicineCategoriesVo result = medicineCategoriesService.getById(id);
        return ResultBean.success(result);
    }

    /**
     * 新增药品分类
     */
    @PostMapping("/create")
    public ResultBean<Long> add(@RequestBody MedicineCategoriesDto dto) {
        Long result = medicineCategoriesService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 更新药品分类
     */
    @PutMapping("/update")
    public ResultBean<Boolean> update(@RequestBody MedicineCategoriesDto dto) {
        Boolean result = medicineCategoriesService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除药品分类
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicineCategoriesService.delete(id);
        return ResultBean.success(result);
    }
}