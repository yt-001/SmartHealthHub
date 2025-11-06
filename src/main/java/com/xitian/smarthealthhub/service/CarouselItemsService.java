package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.CarouselItemsCreateDTO;
import com.xitian.smarthealthhub.domain.dto.CarouselItemsUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.CarouselItems;
import com.xitian.smarthealthhub.domain.query.CarouselItemsQuery;
import com.xitian.smarthealthhub.domain.vo.CarouselItemsVO;

import java.util.List;

/**
 * 轮播图服务接口
 */
public interface CarouselItemsService extends IService<CarouselItems> {
    
    /**
     * 分页查询轮播图
     * @param param 分页参数和查询条件
     * @return 轮播图分页数据
     */
    PageBean<CarouselItemsVO> pageQuery(PageParam<CarouselItemsQuery> param);
    
    /**
     * 查询显示的轮播图列表（用户端使用）
     * @return 轮播图列表
     */
    List<CarouselItemsVO> listDisplayCarouselItems();
    
    /**
     * 根据ID获取轮播图详情
     * @param id 轮播图ID
     * @return 轮播图详情
     */
    CarouselItemsVO getCarouselItemById(Long id);
    
    /**
     * 创建轮播图
     * @param carouselItemsCreateDTO 轮播图创建信息
     * @return 操作结果
     */
    boolean createCarouselItem(CarouselItemsCreateDTO carouselItemsCreateDTO);
    
    /**
     * 更新轮播图
     * @param carouselItemsUpdateDTO 轮播图更新信息
     * @return 操作结果
     */
    boolean updateCarouselItem(CarouselItemsUpdateDTO carouselItemsUpdateDTO);
    
    /**
     * 删除轮播图（逻辑删除）
     * @param id 轮播图ID
     * @return 操作结果
     */
    boolean deleteCarouselItem(Long id);
}