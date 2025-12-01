package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.VideoCategories;
import com.xitian.smarthealthhub.domain.vo.VideoCategoriesVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频分类转换器
 */
public class VideoCategoriesConverter {
    
    /**
     * 将视频分类实体转换为VO
     * @param videoCategories 视频分类实体
     * @return 视频分类VO
     */
    public static VideoCategoriesVO toVO(VideoCategories videoCategories) {
        if (videoCategories == null) {
            return null;
        }
        
        VideoCategoriesVO vo = new VideoCategoriesVO();
        vo.setId(videoCategories.getId());
        vo.setName(videoCategories.getName());
        vo.setDescription(videoCategories.getDescription());
        vo.setSortOrder(videoCategories.getSortOrder());
        vo.setIsEnabled(videoCategories.getIsEnabled());
        return vo;
    }
    
    /**
     * 将视频分类实体列表转换为VO列表
     * @param videoCategoriesList 视频分类实体列表
     * @return 视频分类VO列表
     */
    public static List<VideoCategoriesVO> toVOList(List<VideoCategories> videoCategoriesList) {
        if (videoCategoriesList == null) {
            return null;
        }
        
        List<VideoCategoriesVO> voList = new ArrayList<>();
        for (VideoCategories videoCategory : videoCategoriesList) {
            voList.add(toVO(videoCategory));
        }
        return voList;
    }
}