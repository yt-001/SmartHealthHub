package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.domain.vo.MedicineRecommendationCategoryVO;
import com.xitian.smarthealthhub.domain.vo.MedicineRecommendationMedicineVO;
import com.xitian.smarthealthhub.domain.vo.MedicineRecommendationSubCategoryVO;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import com.xitian.smarthealthhub.service.MedicineCategoryRelationsService;
import com.xitian.smarthealthhub.service.MedicinesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private MedicineCategoriesService medicineCategoriesService;

    @Autowired
    private MedicineCategoryRelationsService medicineCategoryRelationsService;

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

    /**
     * 获取前台调理推荐分类树
     *
     * @return 包含大类、小类及推荐药品的树形结构
     */
    @GetMapping("/recommendation-tree")
    @Operation(summary = "获取前台调理推荐分类树")
    public ResultBean<List<MedicineRecommendationCategoryVO>> getRecommendationTree(
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        String trimmedKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;
        List<MedicineCategoriesVo> bigCategories = medicineCategoriesService.listBigCategories();
        List<MedicineRecommendationCategoryVO> result = new ArrayList<>();

        for (MedicineCategoriesVo big : bigCategories) {
            MedicineRecommendationCategoryVO category = new MedicineRecommendationCategoryVO();
            category.setId(big.getId());
            category.setName(big.getName());
            category.setIcon(resolveCategoryIcon(big.getName()));

            List<MedicineCategoriesVo> subCategories =
                    medicineCategoriesService.listSubCategoriesByParentId(big.getId());
            List<MedicineRecommendationSubCategoryVO> subList = new ArrayList<>();

            int[] palette = new int[]{
                    0xE6F7FF, 0xFFF7E6, 0xF6FFED, 0xE6FFFB, 0xFFF0F6, 0xFFF2E8, 0xF9F0FF
            };
            int index = 0;

            for (MedicineCategoriesVo sub : subCategories) {
                MedicineRecommendationSubCategoryVO subCategory = new MedicineRecommendationSubCategoryVO();
                subCategory.setId(sub.getId());
                subCategory.setName(sub.getName());
                subCategory.setDesc(sub.getDescription());
                int colorInt = palette[index % palette.length];
                String color = "#" + String.format("%06X", colorInt);
                subCategory.setColor(color);
                index++;

                List<Long> medicineIds =
                        medicineCategoryRelationsService.getMedicineIdsByCategoryId(sub.getId());
                List<MedicineRecommendationMedicineVO> medicines = new ArrayList<>();

                if (medicineIds != null && !medicineIds.isEmpty()) {
                    List<Long> limitedIds;
                    if (StringUtils.hasText(trimmedKeyword)) {
                        limitedIds = medicineIds;
                    } else {
                        limitedIds = medicineIds.stream().limit(6).collect(Collectors.toList());
                    }
                    for (Long medicineId : limitedIds) {
                        MedicinesVo vo = medicinesService.getById(medicineId);
                        if (vo == null) {
                            continue;
                        }
                        if (StringUtils.hasText(trimmedKeyword)) {
                            String name = vo.getName();
                            if (!StringUtils.hasText(name) || !name.toLowerCase().contains(trimmedKeyword.toLowerCase())) {
                                continue;
                            }
                        }
                        MedicineRecommendationMedicineVO med = new MedicineRecommendationMedicineVO();
                        med.setId(vo.getId());
                        med.setName(vo.getName());
                        med.setImage(vo.getCoverImageUrl());
                        med.setDesc(vo.getDescription());
                        med.setPrice(vo.getPrice());

                        List<String> tags = new ArrayList<>();
                        if (vo.getIsPrescription() != null) {
                            tags.add(vo.getIsPrescription() == 1 ? "处方药" : "OTC");
                        }
                        if (vo.getSubCategoryName() != null && !vo.getSubCategoryName().isEmpty()) {
                            tags.add(vo.getSubCategoryName());
                        }
                        med.setTags(tags);
                        med.setRecommendationLevel(vo.getRecommendationLevel());

                        medicines.add(med);
                    }
                }

                subCategory.setMedicines(medicines);
                if (!StringUtils.hasText(trimmedKeyword) || (medicines != null && !medicines.isEmpty())) {
                    subList.add(subCategory);
                }
            }

            if (!StringUtils.hasText(trimmedKeyword) || (subList != null && !subList.isEmpty())) {
                category.setSubCategories(subList);
                result.add(category);
            }
        }

        return ResultBean.success(result);
    }

    /**
     * 解析大类名称对应的图标标识
     *
     * @param name 大类名称
     * @return 图标类型字符串
     */
    private String resolveCategoryIcon(String name) {
        if (name == null) {
            return "default";
        }
        if (name.contains("中药")) {
            return "tcm";
        }
        if (name.contains("西药")) {
            return "western";
        }
        if (name.contains("营养") || name.contains("保健")) {
            return "supplement";
        }
        return "default";
    }
}
