package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 健康文章视图对象
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HealthArticleVO extends BaseVO {
    
    /**
     * 文章标题
     */
    private String title;
    
    /**
     * 文章摘要
     */
    private String summary;
    
    /**
     * 文章内容
     */
    private String content;
    
    /**
     * 文章封面图片URL
     */
    private String coverImageUrl;
    
    /**
     * 作者ID
     */
    private Long authorId;
    
    /**
     * 作者姓名
     */
    private String authorName;
    
    /**
     * 科室ID
     */
    private Integer deptId;
    
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
     * 点赞次数
     */
    private Integer likeCount;
    
    /**
     * 是否置顶：0 否 1 是
     */
    private Byte isTop;
    
    /**
     * 状态：0 草稿 1 已发布 2 已下架 3 审核中 4 未通过审核
     */
    private Byte status;
}