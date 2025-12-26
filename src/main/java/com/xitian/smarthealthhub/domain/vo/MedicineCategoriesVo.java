package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * 药品分类VO
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MedicineCategoriesVo extends BaseVO {
    /**
     * 父分类ID（为空表示一级分类）
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否启用：0 否 1 是
     */
    private Integer isEnabled;
}