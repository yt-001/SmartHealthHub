package com.xitian.smarthealthhub.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageParam<T> implements Serializable {
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
    
    // 添加显式的构造函数用于Jackson反序列化
    @JsonCreator
    public PageParam(@JsonProperty("pageNum") int pageNum,
                     @JsonProperty("pageSize") int pageSize,
                     @JsonProperty("sortField") String sortField,
                     @JsonProperty("sortDirection") Sort.Direction sortDirection,
                     @JsonProperty("query") T query) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.query = query;
    }
    
    // 无参构造函数
    public PageParam() {
    }
}