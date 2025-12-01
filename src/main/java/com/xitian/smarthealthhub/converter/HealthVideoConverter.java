package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.HealthVideos;
import com.xitian.smarthealthhub.domain.vo.HealthVideoVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康视频转换器
 */
public class HealthVideoConverter {
    
    /**
     * 将健康视频实体转换为VO对象
     * @param healthVideos 健康视频实体
     * @return 健康视频VO对象
     */
    public static HealthVideoVO toVO(HealthVideos healthVideos) {
        if (healthVideos == null) {
            return null;
        }
        
        HealthVideoVO vo = new HealthVideoVO();
        vo.setId(healthVideos.getId());
        vo.setCreatedAt(healthVideos.getCreatedAt());
        vo.setUpdatedAt(healthVideos.getUpdatedAt());
        vo.setTitle(healthVideos.getTitle());
        vo.setDescription(healthVideos.getDescription());
        vo.setCoverImageUrl(healthVideos.getCoverImageUrl());
        vo.setVideoUrl(healthVideos.getVideoUrl());
        vo.setDuration(healthVideos.getDuration());
        vo.setAuthorId(healthVideos.getAuthorId());
        vo.setAuthorName(healthVideos.getAuthorName());
        vo.setViewCount(healthVideos.getViewCount());
        vo.setLikeCount(healthVideos.getLikeCount());
        vo.setCommentCount(healthVideos.getCommentCount());
        vo.setIsTop(healthVideos.getIsTop());
        vo.setStatus(healthVideos.getStatus());
        return vo;
    }
    
    /**
     * 将健康视频实体列表转换为VO对象列表
     * @param healthVideosList 健康视频实体列表
     * @return 健康视频VO对象列表
     */
    public static List<HealthVideoVO> toVOList(List<HealthVideos> healthVideosList) {
        if (healthVideosList == null) {
            return null;
        }
        
        List<HealthVideoVO> voList = new ArrayList<>();
        for (HealthVideos healthVideo : healthVideosList) {
            voList.add(toVO(healthVideo));
        }
        return voList;
    }
}