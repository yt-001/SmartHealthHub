package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 健康文章管理员列表VO（包含分类信息）
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "健康文章管理员列表VO（包含分类信息）")
public class HealthArticleAdminVO extends HealthArticleReviewVO {
    
    @Schema(description = "分类信息列表")
    private List<CategorySimpleVO> categories;
}