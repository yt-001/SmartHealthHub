package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.HealthVideos;
import com.xitian.smarthealthhub.domain.vo.HealthVideoReviewVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康视频审核列表转换器
 */
public class HealthVideoReviewConverter {
    
    /**
     * 将健康视频实体转换为审核列表VO对象
     * @param healthVideos 健康视频实体
     * @return 健康视频审核列表VO对象
     */
    public static HealthVideoReviewVO toReviewVO(HealthVideos healthVideos) {
        if (healthVideos == null) {
            return null;
        }
        
        HealthVideoReviewVO vo = new HealthVideoReviewVO();
        vo.setId(healthVideos.getId());
        vo.setTitle(healthVideos.getTitle());
        vo.setAuthorName(healthVideos.getAuthorName());
        vo.setViewCount(healthVideos.getViewCount());
        vo.setCoverImageUrl(healthVideos.getCoverImageUrl());
        vo.setDuration(healthVideos.getDuration());
        vo.setStatus(healthVideos.getStatus());
        vo.setCreatedAt(healthVideos.getCreatedAt());
        return vo;
    }
    
    /**
     * 将健康视频实体列表转换为审核列表VO对象列表
     * @param healthVideosList 健康视频实体列表
     * @return 健康视频审核列表VO对象列表
     */
    public static List<HealthVideoReviewVO> toReviewVOList(List<HealthVideos> healthVideosList) {
        if (healthVideosList == null) {
            return null;
        }
        
        List<HealthVideoReviewVO> voList = new ArrayList<>();
        for (HealthVideos healthVideo : healthVideosList) {
            voList.add(toReviewVO(healthVideo));
        }
        return voList;
    }
}