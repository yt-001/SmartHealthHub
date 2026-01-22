package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.entity.PharmacyLocationsEntity;
import com.xitian.smarthealthhub.service.PharmacyLocationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy-locations")
@Tag(name = "取药地点管理")
@Slf4j
public class PharmacyLocationsController {

    @Autowired
    private PharmacyLocationsService pharmacyLocationsService;

    @GetMapping("/list")
    @Operation(summary = "获取所有启用的取药地点")
    public ResultBean<List<PharmacyLocationsEntity>> list() {
        LambdaQueryWrapper<PharmacyLocationsEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PharmacyLocationsEntity::getIsEnabled, 1)
               .orderByAsc(PharmacyLocationsEntity::getSortOrder);
        return ResultBean.success(pharmacyLocationsService.list(wrapper));
    }
}
