package com.xitian.smarthealthhub.domain.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 健康文章审核列表视图对象
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HealthArticleReviewVO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 文章标题
     */
    private String title;
    
    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 文章分类/标签
     */
    private String category;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 状态：0 草稿 1 已发布 2 已下架 3 审核中 4 未通过审核
     */
    private Byte status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}