package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.HealthVideoCommentCreateDTO;
import com.xitian.smarthealthhub.domain.query.HealthVideoCommentQuery;
import com.xitian.smarthealthhub.domain.vo.HealthVideoCommentVO;
import com.xitian.smarthealthhub.service.HealthVideoCommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 健康视频评论控制器
 */
@Tag(name = "HealthVideoCommentsController", description = "健康视频评论接口")
@RestController
@RequestMapping("/health-video-comments")
public class HealthVideoCommentsController {

    @Resource
    private HealthVideoCommentsService healthVideoCommentsService;

    /**
     * 分页查询健康视频评论
     * @param param 分页参数和查询条件
     * @return 健康视频评论分页数据
     */
    @Operation(summary = "分页查询健康视频评论")
    @PostMapping("/page")
    public ResultBean<PageBean<HealthVideoCommentVO>> page(@RequestBody PageParam<HealthVideoCommentQuery> param) {
        PageBean<HealthVideoCommentVO> pageBean = healthVideoCommentsService.pageQuery(param);
        return ResultBean.success(pageBean);
    }

    /**
     * 根据视频ID获取评论列表
     * @param videoId 视频ID
     * @return 评论列表
     */
    @Operation(summary = "根据视频ID获取评论列表")
    @GetMapping("/video/{videoId}")
    public ResultBean<List<HealthVideoCommentVO>> getCommentsByVideoId(@PathVariable Long videoId) {
        List<HealthVideoCommentVO> commentVOList = healthVideoCommentsService.getCommentsByVideoId(videoId);
        return ResultBean.success(commentVOList);
    }

    /**
     * 根据ID获取健康视频评论详情
     * @param id 评论ID
     * @return 健康视频评论详情
     */
    @Operation(summary = "根据ID获取健康视频评论详情")
    @GetMapping("/{id}")
    public ResultBean<HealthVideoCommentVO> getHealthVideoCommentById(@PathVariable Long id) {
        HealthVideoCommentVO vo = healthVideoCommentsService.getHealthVideoCommentById(id);
        if (vo == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "评论不存在或已被删除");
        }
        return ResultBean.success(vo);
    }

    /**
     * 创建健康视频评论
     * @param commentCreateDTO 评论创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建健康视频评论")
    @PostMapping("/create")
    public ResultBean<String> createHealthVideoComment(@Valid @RequestBody HealthVideoCommentCreateDTO commentCreateDTO) {
        try {
            boolean saved = healthVideoCommentsService.createHealthVideoComment(commentCreateDTO);
            if (saved) {
                return ResultBean.success("评论创建成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "评论创建失败");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "评论创建过程中发生错误: " + e.getMessage());
        }
    }

    /**
     * 删除健康视频评论（逻辑删除）
     * @param id 评论ID
     * @return 操作结果
     */
    @Operation(summary = "删除健康视频评论")
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> deleteHealthVideoComment(@PathVariable Long id) {
        try {
            boolean deleted = healthVideoCommentsService.deleteHealthVideoComment(id);
            if (deleted) {
                return ResultBean.success("评论删除成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "评论不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "评论删除过程中发生错误: " + e.getMessage());
        }
    }
}