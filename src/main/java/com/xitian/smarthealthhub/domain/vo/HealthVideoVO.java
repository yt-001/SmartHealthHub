package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 健康视频视图对象
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HealthVideoVO extends BaseVO {
    
    /**
     * 视频标题
     */
    private String title;
    
    /**
     * 视频描述
     */
    private String description;
    
    /**
     * 视频封面图片URL
     */
    private String coverImageUrl;
    
    /**
     * 视频文件URL
     */
    private String videoUrl;
    
    /**
     * 视频时长（秒）
     */
    private Integer duration;
    
    /**
     * 作者ID
     */
    private Long authorId;
    
    /**
     * 作者姓名
     */
    private String authorName;
    
    /**
     * 视频分类/标签
     */
    private String category;
    
    /**
     * 观看次数
     */
    private Integer viewCount;
    
    /**
     * 点赞次数
     */
    private Integer likeCount;
    
    /**
     * 评论数量
     */
    private Integer commentCount;
    
    /**
     * 是否置顶：0 否 1 是
     */
    private Byte isTop;
    
    /**
     * 状态：0 草稿 1 已发布 2 已下架
     */
    private Byte status;
}