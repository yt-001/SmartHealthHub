package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.HealthVideoComments;
import com.xitian.smarthealthhub.domain.vo.HealthVideoCommentVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康视频评论转换器
 */
public class HealthVideoCommentConverter {
    
    /**
     * 将健康视频评论实体转换为VO
     * @param healthVideoComments 健康视频评论实体
     * @return 健康视频评论VO
     */
    public static HealthVideoCommentVO toVO(HealthVideoComments healthVideoComments) {
        if (healthVideoComments == null) {
            return null;
        }
        
        HealthVideoCommentVO vo = new HealthVideoCommentVO();
        vo.setId(healthVideoComments.getId());
        vo.setVideoId(healthVideoComments.getVideoId());
        vo.setUserId(healthVideoComments.getUserId());
        vo.setUserName(healthVideoComments.getUserName());
        vo.setUserAvatar(healthVideoComments.getUserAvatar());
        vo.setContent(healthVideoComments.getContent());
        vo.setLikeCount(healthVideoComments.getLikeCount());
        vo.setParentId(healthVideoComments.getParentId());
        vo.setReplyToId(healthVideoComments.getReplyToId());
        vo.setReplyToName(healthVideoComments.getReplyToName());
        vo.setStatus(healthVideoComments.getStatus());
        vo.setCreatedAt(healthVideoComments.getCreatedAt());
        return vo;
    }
    
    /**
     * 将健康视频评论实体列表转换为VO列表
     * @param healthVideoCommentsList 健康视频评论实体列表
     * @return 健康视频评论VO列表
     */
    public static List<HealthVideoCommentVO> toVOList(List<HealthVideoComments> healthVideoCommentsList) {
        if (healthVideoCommentsList == null) {
            return null;
        }
        
        List<HealthVideoCommentVO> voList = new ArrayList<>();
        for (HealthVideoComments comment : healthVideoCommentsList) {
            voList.add(toVO(comment));
        }
        return voList;
    }
}