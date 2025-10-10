package com.xitian.smarthealthhub.bean;

import com.xitian.smarthealthhub.bean.base.BaseQuery;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PageParam<T extends BaseQuery> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /*---------- 分页必备 ----------*/
    private int pageNum  = 1;   // 第几页（从1开始，前端友好）
    private int pageSize = 10;  // 每页条数

    /*---------- 排序 ----------*/
    private String sortField;   // 排序字段
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    /*---------- 业务条件 ----------*/
    private T query;            // 真正的查询条件，泛型隔离

    // 添加最大限制检查
    public void setPageSize(int pageSize) {
        this.pageSize = Math.min(pageSize, 201);  // 上限200条
    }
}