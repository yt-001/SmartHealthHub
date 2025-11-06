package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.CarouselItems;
import com.xitian.smarthealthhub.domain.vo.CarouselItemsVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 轮播图转换器
 */
public class CarouselItemsConverter {
    
    /**
     * 将CarouselItems实体转换为CarouselItemsVO视图对象
     * @param carouselItems 轮播图实体
     * @return 轮播图视图对象
     */
    public static CarouselItemsVO toVO(CarouselItems carouselItems) {
        if (carouselItems == null) {
            return null;
        }
        
        CarouselItemsVO vo = new CarouselItemsVO();
        BeanUtils.copyProperties(carouselItems, vo);
        return vo;
    }
    
    /**
     * 将CarouselItemsVO视图对象转换为CarouselItems实体
     * @param carouselItemsVO 轮播图视图对象
     * @return 轮播图实体
     */
    public static CarouselItems toEntity(CarouselItemsVO carouselItemsVO) {
        if (carouselItemsVO == null) {
            return null;
        }
        
        CarouselItems entity = new CarouselItems();
        BeanUtils.copyProperties(carouselItemsVO, entity);
        return entity;
    }
    
    /**
     * 将CarouselItems实体列表转换为CarouselItemsVO列表
     * @param carouselItemsList 轮播图实体列表
     * @return 轮播图VO列表
     */
    public static List<CarouselItemsVO> toVOList(List<CarouselItems> carouselItemsList) {
        if (carouselItemsList == null) {
            return null;
        }
        
        return carouselItemsList.stream()
                .map(CarouselItemsConverter::toVO)
                .collect(Collectors.toList());
    }
}