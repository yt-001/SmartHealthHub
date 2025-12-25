package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.service.MedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 药品信息主表控制器
 */
@RestController
@RequestMapping("/medicines")
public class MedicinesController {

    @Autowired
    private MedicinesService medicinesService;

    /**
     * 分页查询药品信息
     */
    @GetMapping("/page")
    public ResultBean<PageBean<MedicinesVo>> pageQuery(MedicinesQuery query) {
        PageBean<MedicinesVo> result = medicinesService.pageQuery(query);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询药品信息
     */
    @GetMapping("/{id}")
    public ResultBean<MedicinesVo> getById(@PathVariable Long id) {
        MedicinesVo result = medicinesService.getById(id);
        return ResultBean.success(result);
    }

    /**
     * 新增药品信息
     */
    @PostMapping("/create")
    public ResultBean<Long> add(@RequestBody MedicinesDto dto) {
        Long result = medicinesService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 更新药品信息
     */
    @PutMapping("/update")
    public ResultBean<Boolean> update(@RequestBody MedicinesDto dto) {
        Boolean result = medicinesService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除药品信息
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicinesService.delete(id);
        return ResultBean.success(result);
    }
}