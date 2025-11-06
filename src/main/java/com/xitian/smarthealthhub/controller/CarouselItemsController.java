package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.CarouselItemsCreateDTO;
import com.xitian.smarthealthhub.domain.dto.CarouselItemsUpdateDTO;
import com.xitian.smarthealthhub.domain.query.CarouselItemsQuery;
import com.xitian.smarthealthhub.domain.vo.CarouselItemsVO;
import com.xitian.smarthealthhub.service.CarouselItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 轮播图控制器
 */
@Tag(name = "CarouselItemsController", description = "轮播图接口")
@RestController
@RequestMapping("/carousel-items")
public class CarouselItemsController {
    
    @Resource
    private CarouselItemsService carouselItemsService;
    
    /**
     * 分页查询轮播图
     * @param param 分页参数和查询条件
     * @return 轮播图分页数据
     */
    @Operation(summary = "分页查询轮播图")
    @PostMapping("/page")
    public ResultBean<PageBean<CarouselItemsVO>> page(@RequestBody PageParam<CarouselItemsQuery> param) {
        PageBean<CarouselItemsVO> pageBean = carouselItemsService.pageQuery(param);
        return ResultBean.success(pageBean);
    }
    
    /**
     * 查询显示的轮播图列表（用户端接口）
     * @return 轮播图列表
     */
    @Operation(summary = "查询显示的轮播图列表")
    @GetMapping("/display")
    public ResultBean<List<CarouselItemsVO>> listDisplayCarouselItems() {
        List<CarouselItemsVO> carouselItems = carouselItemsService.listDisplayCarouselItems();
        return ResultBean.success(carouselItems);
    }
    
    /**
     * 根据ID获取轮播图详情
     * @param id 轮播图ID
     * @return 轮播图详情
     */
    @Operation(summary = "根据ID获取轮播图详情")
    @GetMapping("/{id}")
    public ResultBean<CarouselItemsVO> getCarouselItemById(@PathVariable Long id) {
        CarouselItemsVO vo = carouselItemsService.getCarouselItemById(id);
        if (vo == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "轮播图不存在或已被删除");
        }
        return ResultBean.success(vo);
    }
    
    /**
     * 创建轮播图
     * @param carouselItemsCreateDTO 轮播图创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建轮播图")
    @PostMapping("/create")
    public ResultBean<String> createCarouselItem(@Valid @RequestBody CarouselItemsCreateDTO carouselItemsCreateDTO) {
        try {
            boolean saved = carouselItemsService.createCarouselItem(carouselItemsCreateDTO);
            if (saved) {
                return ResultBean.success("轮播图创建成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "轮播图创建失败");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "轮播图创建过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 更新轮播图
     * @param carouselItemsUpdateDTO 轮播图更新信息
     * @return 操作结果
     */
    @Operation(summary = "更新轮播图")
    @PutMapping("/update")
    public ResultBean<String> updateCarouselItem(@Valid @RequestBody CarouselItemsUpdateDTO carouselItemsUpdateDTO) {
        try {
            boolean updated = carouselItemsService.updateCarouselItem(carouselItemsUpdateDTO);
            if (updated) {
                return ResultBean.success("轮播图更新成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "轮播图不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "轮播图更新过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 删除轮播图（逻辑删除）
     * @param id 轮播图ID
     * @return 操作结果
     */
    @Operation(summary = "删除轮播图")
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> deleteCarouselItem(@PathVariable Long id) {
        try {
            boolean deleted = carouselItemsService.deleteCarouselItem(id);
            if (deleted) {
                return ResultBean.success("轮播图删除成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "轮播图不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "轮播图删除过程中发生错误: " + e.getMessage());
        }
    }
}