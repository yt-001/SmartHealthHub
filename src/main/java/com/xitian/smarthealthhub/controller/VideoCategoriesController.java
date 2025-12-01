package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.VideoCategoriesCreateDTO;
import com.xitian.smarthealthhub.domain.dto.VideoCategoriesUpdateDTO;
import com.xitian.smarthealthhub.domain.query.VideoCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;
import com.xitian.smarthealthhub.domain.vo.VideoCategoriesVO;
import com.xitian.smarthealthhub.service.VideoCategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 视频分类控制器
 */
@Tag(name = "VideoCategoriesController", description = "视频分类接口")
@RestController
@RequestMapping("/video-categories")
public class VideoCategoriesController {
    
    @Resource
    private VideoCategoriesService videoCategoriesService;
    
    /**
     * 分页查询视频分类
     * @param param 分页参数和查询条件
     * @return 视频分类分页数据
     */
    @Operation(summary = "分页查询视频分类")
    @PostMapping("/page")
    public ResultBean<PageBean<VideoCategoriesVO>> page(@RequestBody PageParam<VideoCategoriesQuery> param) {
        PageBean<VideoCategoriesVO> pageBean = videoCategoriesService.pageQuery(param);
        return ResultBean.success(pageBean);
    }
    
    /**
     * 获取所有启用的视频分类（简化信息）
     * @return 视频分类简化信息列表
     */
    @Operation(summary = "获取所有启用的视频分类（简化信息）")
    @GetMapping("/simple-list")
    public ResultBean<List<CategorySimpleVO>> getAllEnabledCategoriesSimple() {
        List<CategorySimpleVO> categories = videoCategoriesService.getAllEnabledCategoriesSimple();
        return ResultBean.success(categories);
    }
    
    /**
     * 获取所有启用的视频分类
     * @return 视频分类列表
     */
    @Operation(summary = "获取所有启用的视频分类")
    @GetMapping("/enabled-list")
    public ResultBean<List<VideoCategoriesVO>> getAllEnabledCategories() {
        List<VideoCategoriesVO> categories = videoCategoriesService.getAllEnabledCategories();
        return ResultBean.success(categories);
    }
    
    /**
     * 根据ID获取视频分类详情
     * @param id 分类ID
     * @return 视频分类详情
     */
    @Operation(summary = "根据ID获取视频分类详情")
    @GetMapping("/{id}")
    public ResultBean<VideoCategoriesVO> getVideoCategoryById(@PathVariable Long id) {
        VideoCategoriesVO vo = videoCategoriesService.getVideoCategoryById(id);
        if (vo == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "分类不存在或已被删除");
        }
        return ResultBean.success(vo);
    }
    
    /**
     * 创建视频分类
     * @param videoCategoriesCreateDTO 视频分类创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建视频分类")
    @PostMapping("/create")
    public ResultBean<String> createVideoCategory(@Valid @RequestBody VideoCategoriesCreateDTO videoCategoriesCreateDTO) {
        try {
            boolean saved = videoCategoriesService.createVideoCategory(videoCategoriesCreateDTO);
            if (saved) {
                return ResultBean.success("分类创建成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "分类创建失败");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "分类创建过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 更新视频分类
     * @param videoCategoriesUpdateDTO 视频分类更新信息
     * @return 操作结果
     */
    @Operation(summary = "更新视频分类")
    @PutMapping("/update")
    public ResultBean<String> updateVideoCategory(@Valid @RequestBody VideoCategoriesUpdateDTO videoCategoriesUpdateDTO) {
        try {
            boolean updated = videoCategoriesService.updateVideoCategory(videoCategoriesUpdateDTO);
            if (updated) {
                return ResultBean.success("分类更新成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "分类不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "分类更新过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 删除视频分类（逻辑删除）
     * @param id 分类ID
     * @return 操作结果
     */
    @Operation(summary = "删除视频分类")
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> deleteVideoCategory(@PathVariable Long id) {
        try {
            boolean deleted = videoCategoriesService.deleteVideoCategory(id);
            if (deleted) {
                return ResultBean.success("分类删除成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "分类不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "分类删除过程中发生错误: " + e.getMessage());
        }
    }
}