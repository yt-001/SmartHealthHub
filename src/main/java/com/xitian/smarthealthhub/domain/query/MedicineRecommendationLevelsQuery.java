package com.xitian.smarthealthhub.domain.query;

/**
 * 药品推荐级别查询条件
 */
public class MedicineRecommendationLevelsQuery {
    /**
     * 推荐级别名称
     */
    private String name;

    /**
     * 是否启用：0 否 1 是
     */
    private Integer isEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }
}

