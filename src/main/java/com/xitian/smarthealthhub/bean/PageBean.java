package com.xitian.smarthealthhub.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class PageBean<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long total;      // 总记录数
    private List<T> list;    // 当前页数据
    private int  pageNum;    // 当前页码
    private int  pageSize;   // 每页条数
    private int  pages;      // 总页数（前端懒得算）

    /* 快速构造 */
    public static <T> PageBean<T> of(List<T> list, long total, PageParam<?> param) {
        PageBean<T> pb = new PageBean<>();
        pb.setList(list);
        pb.setTotal(total);
        pb.setPageNum(param.getPageNum());
        pb.setPageSize(param.getPageSize());
        pb.setPages((int) Math.ceil((double) total / param.getPageSize()));
        return pb;
    }
}