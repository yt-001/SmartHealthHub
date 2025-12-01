package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.ArticleCategoriesCreateDTO;
import com.xitian.smarthealthhub.domain.dto.ArticleCategoriesUpdateDTO;
import com.xitian.smarthealthhub.domain.query.ArticleCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.ArticleCategoriesVO;
import com.xitian.smarthealthhub.service.ArticleCategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 文章分类控制器
 */
@Tag(name = "ArticleCategoriesController", description = "文章分类接口")
@RestController
@RequestMapping("/article-categories")
public class ArticleCategoriesController {
    
    @Resource
    private ArticleCategoriesService articleCategoriesService;
    
    /**
     * 分页查询文章分类
     * @param param 分页参数和查询条件
     * @return 文章分类分页数据
     */
    @Operation(summary = "分页查询文章分类")
    @PostMapping("/page")
    public ResultBean<PageBean<ArticleCategoriesVO>> page(@RequestBody PageParam<ArticleCategoriesQuery> param) {
        PageBean<ArticleCategoriesVO> pageBean = articleCategoriesService.pageQuery(param);
        return ResultBean.success(pageBean);
    }
    
    /**
     * 获取所有启用的文章分类
     * @return 文章分类列表
     */
    @Operation(summary = "获取所有启用的文章分类")
    @GetMapping("/enabled-list")
    public ResultBean<List<ArticleCategoriesVO>> getAllEnabledCategories() {
        List<ArticleCategoriesVO> categories = articleCategoriesService.getAllEnabledCategories();
        return ResultBean.success(categories);
    }
    
    /**
     * 根据ID获取文章分类详情
     * @param id 分类ID
     * @return 文章分类详情
     */
    @Operation(summary = "根据ID获取文章分类详情")
    @GetMapping("/{id}")
    public ResultBean<ArticleCategoriesVO> getArticleCategoryById(@PathVariable Long id) {
        ArticleCategoriesVO vo = articleCategoriesService.getArticleCategoryById(id);
        if (vo == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "分类不存在或已被删除");
        }
        return ResultBean.success(vo);
    }
    
    /**
     * 创建文章分类
     * @param articleCategoriesCreateDTO 文章分类创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建文章分类")
    @PostMapping("/create")
    public ResultBean<String> createArticleCategory(@Valid @RequestBody ArticleCategoriesCreateDTO articleCategoriesCreateDTO) {
        try {
            boolean saved = articleCategoriesService.createArticleCategory(articleCategoriesCreateDTO);
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
     * 更新文章分类
     * @param articleCategoriesUpdateDTO 文章分类更新信息
     * @return 操作结果
     */
    @Operation(summary = "更新文章分类")
    @PutMapping("/update")
    public ResultBean<String> updateArticleCategory(@Valid @RequestBody ArticleCategoriesUpdateDTO articleCategoriesUpdateDTO) {
        try {
            boolean updated = articleCategoriesService.updateArticleCategory(articleCategoriesUpdateDTO);
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
     * 删除文章分类（逻辑删除）
     * @param id 分类ID
     * @return 操作结果
     */
    @Operation(summary = "删除文章分类")
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> deleteArticleCategory(@PathVariable Long id) {
        try {
            boolean deleted = articleCategoriesService.deleteArticleCategory(id);
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