package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.HealthArticleCreateDTO;
import com.xitian.smarthealthhub.domain.dto.HealthArticleUpdateDTO;
import com.xitian.smarthealthhub.domain.query.HealthArticleQuery;
import com.xitian.smarthealthhub.domain.vo.HealthArticleVO;
import com.xitian.smarthealthhub.service.HealthArticlesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 健康文章控制器
 */
@Tag(name = "HealthArticleController", description = "健康文章接口")
@RestController
@RequestMapping("/health-articles")
public class HealthArticleController {
    
    @Resource
    private HealthArticlesService healthArticlesService;
    
    /**
     * 分页查询健康文章
     * @param param 分页参数和查询条件
     * @return 健康文章分页数据
     */
    @Operation(summary = "分页查询健康文章")
    @PostMapping("/page")
    public ResultBean<PageBean<HealthArticleVO>> page(@RequestBody PageParam<HealthArticleQuery> param) {
        PageBean<HealthArticleVO> pageBean = healthArticlesService.pageQuery(param);
        return ResultBean.success(pageBean);
    }
    
    /**
     * 根据ID获取健康文章详情
     * @param id 文章ID
     * @return 健康文章详情
     */
    @Operation(summary = "根据ID获取健康文章详情")
    @GetMapping("/{id}/view")
    public ResultBean<HealthArticleVO> getHealthArticleById(@PathVariable Long id) {
        HealthArticleVO vo = healthArticlesService.getHealthArticleById(id);
        if (vo == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "文章不存在或已被删除");
        }
        return ResultBean.success(vo);
    }
    
    /**
     * 创建健康文章
     * @param articleCreateDTO 文章创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建健康文章")
    @PostMapping("/create")
    public ResultBean<String> createHealthArticle(@Valid @RequestBody HealthArticleCreateDTO articleCreateDTO) {
        try {
            boolean saved = healthArticlesService.createHealthArticle(articleCreateDTO);
            if (saved) {
                return ResultBean.success("文章创建成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "文章创建失败");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "文章创建过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 更新健康文章
     * @param articleUpdateDTO 文章更新信息
     * @return 操作结果
     */
    @Operation(summary = "更新健康文章")
    @PutMapping("/update")
    public ResultBean<String> updateHealthArticle(@Valid @RequestBody HealthArticleUpdateDTO articleUpdateDTO) {
        try {
            boolean updated = healthArticlesService.updateHealthArticle(articleUpdateDTO);
            if (updated) {
                return ResultBean.success("文章更新成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "文章不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "文章更新过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 删除健康文章（逻辑删除）
     * @param id 文章ID
     * @return 操作结果
     */
    @Operation(summary = "删除健康文章")
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> deleteHealthArticle(@PathVariable Long id) {
        try {
            boolean deleted = healthArticlesService.deleteHealthArticle(id);
            if (deleted) {
                return ResultBean.success("文章删除成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "文章不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "文章删除过程中发生错误: " + e.getMessage());
        }
    }
}