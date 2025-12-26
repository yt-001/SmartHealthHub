package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.service.MedicinesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
 * 药品控制器
 * 
 * @author 
 * @date 2025/02/04
 */
@RestController
@RequestMapping("/medicines")
@Tag(name = "药品管理")
@Slf4j
public class MedicinesController {

    @Autowired
    private MedicinesService medicinesService;

    /**
     * 分页查询药品信息
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询药品信息")
    public ResultBean<PageBean<MedicinesVo>> pageQuery(@RequestBody PageParam<MedicinesQuery> param) {
        PageBean<MedicinesVo> result = medicinesService.pageQuery(param);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询药品信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询药品信息")
    public ResultBean<MedicinesVo> getById(@PathVariable Long id) {
        MedicinesVo result = medicinesService.getById(id);
        return ResultBean.success(result);
    }

    /**
     * 新增药品信息
     */
    @PostMapping("/create")
    @Operation(summary = "新增药品信息")
    public ResultBean<Long> add(@RequestBody @Valid MedicinesDto dto) {
        Long result = medicinesService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 更新药品信息
     */
    @PutMapping("/update")
    @Operation(summary = "更新药品信息")
    public ResultBean<Boolean> update(@RequestBody @Valid MedicinesDto dto) {
        Boolean result = medicinesService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除药品信息
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除药品信息")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicinesService.delete(id);
        return ResultBean.success(result);
    }
}
