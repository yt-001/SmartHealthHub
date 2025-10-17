package com.xitian.smarthealthhub.bean.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "通用查询父类，所有业务查询对象继承它")
public abstract class BaseQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /*========== 以下字段完全可选，按需覆写 ==========*/
    @Schema(description = "关键字模糊搜索")
    private String keyword;

    @Schema(description = "创建时间，格式：yyyy-MM-dd")
    private String createdAt;

    @Schema(description = "更新时间")
    private String updatedAt;
}