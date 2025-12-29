package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicineTagsDto;
import com.xitian.smarthealthhub.domain.query.MedicineTagsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineTagsVo;
import com.xitian.smarthealthhub.service.MedicineTagsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

/**
 * 药品标签控制器
 */
@RestController
@RequestMapping("/medicine-tags")
@Tag(name = "药品标签管理")
@Slf4j
public class MedicineTagsController {

    @Autowired
    private MedicineTagsService medicineTagsService;

    /**
     * 分页查询药品标签
     *
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询药品标签")
    public ResultBean<PageBean<MedicineTagsVo>> pageQuery(@RequestBody PageParam<MedicineTagsQuery> param) {
        PageBean<MedicineTagsVo> result = medicineTagsService.pageQuery(param);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询药品标签
     *
     * @param id 标签ID
     * @return 标签详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询药品标签")
    public ResultBean<MedicineTagsVo> getById(@PathVariable Long id) {
        MedicineTagsVo result = medicineTagsService.getById(id);
        return ResultBean.success(result);
    }

    /**
     * 新增药品标签
     *
     * @param dto 标签数据传输对象
     * @return 新增记录ID
     */
    @PostMapping("/create")
    @Operation(summary = "新增药品标签")
    public ResultBean<Long> add(@RequestBody @Valid MedicineTagsDto dto) {
        Long result = medicineTagsService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 更新药品标签
     *
     * @param dto 标签数据传输对象
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新药品标签")
    public ResultBean<Boolean> update(@RequestBody @Valid MedicineTagsDto dto) {
        Boolean result = medicineTagsService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除药品标签
     *
     * @param id 标签ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除药品标签")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicineTagsService.delete(id);
        return ResultBean.success(result);
    }
}

