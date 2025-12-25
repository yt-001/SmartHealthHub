package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 药品与分类的关联表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MedicineCategoryRelationsVo extends BaseVO {
    /**
     * 药品ID
     */
    private Long medicineId;

    /**
     * 分类ID
     */
    private Long categoryId;
}