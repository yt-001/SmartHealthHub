package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicineRecommendationLevelsDto;
import com.xitian.smarthealthhub.domain.query.MedicineRecommendationLevelsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineRecommendationLevelsVo;
import com.xitian.smarthealthhub.service.MedicineRecommendationLevelsService;
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
 * 药品推荐级别控制器
 */
@RestController
@RequestMapping("/medicine-recommendation-levels")
@Tag(name = "药品推荐级别管理")
@Slf4j
public class MedicineRecommendationLevelsController {

    @Autowired
    private MedicineRecommendationLevelsService medicineRecommendationLevelsService;

    /**
     * 分页查询推荐级别
     *
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询药品推荐级别")
    public ResultBean<PageBean<MedicineRecommendationLevelsVo>> pageQuery(
            @RequestBody PageParam<MedicineRecommendationLevelsQuery> param) {
        PageBean<MedicineRecommendationLevelsVo> result = medicineRecommendationLevelsService.pageQuery(param);
        return ResultBean.success(result);
    }

    /**
     * 根据ID查询推荐级别
     *
     * @param id 推荐级别ID
     * @return 推荐级别详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询药品推荐级别")
    public ResultBean<MedicineRecommendationLevelsVo> getById(@PathVariable Long id) {
        MedicineRecommendationLevelsVo result = medicineRecommendationLevelsService.getById(id);
        return ResultBean.success(result);
    }

    /**
     * 新增推荐级别
     *
     * @param dto 推荐级别数据传输对象
     * @return 新增记录ID
     */
    @PostMapping("/create")
    @Operation(summary = "新增药品推荐级别")
    public ResultBean<Long> add(@RequestBody @Valid MedicineRecommendationLevelsDto dto) {
        Long result = medicineRecommendationLevelsService.add(dto);
        return ResultBean.success(result);
    }

    /**
     * 更新推荐级别
     *
     * @param dto 推荐级别数据传输对象
     * @return 是否更新成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新药品推荐级别")
    public ResultBean<Boolean> update(@RequestBody @Valid MedicineRecommendationLevelsDto dto) {
        Boolean result = medicineRecommendationLevelsService.update(dto);
        return ResultBean.success(result);
    }

    /**
     * 删除推荐级别
     *
     * @param id 推荐级别ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除药品推荐级别")
    public ResultBean<Boolean> delete(@PathVariable Long id) {
        Boolean result = medicineRecommendationLevelsService.delete(id);
        return ResultBean.success(result);
    }
}

