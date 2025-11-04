package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.HealthVideoCreateDTO;
import com.xitian.smarthealthhub.domain.dto.HealthVideoUpdateDTO;
import com.xitian.smarthealthhub.domain.query.HealthVideoQuery;
import com.xitian.smarthealthhub.domain.vo.HealthVideoVO;
import com.xitian.smarthealthhub.domain.vo.HealthVideoReviewVO;
import com.xitian.smarthealthhub.service.HealthVideosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 健康视频控制器
 */
@Tag(name = "HealthVideosController", description = "健康视频接口")
@RestController
@RequestMapping("/health-videos")
public class HealthVideosController {

    @Resource
    private HealthVideosService healthVideosService;

    /**
     * 分页查询健康视频（管理端接口）
     * @param param 分页参数和查询条件
     * @return 健康视频分页数据
     */
    @Operation(summary = "分页查询健康视频（管理端）")
    @PostMapping("/page")
    public ResultBean<PageBean<HealthVideoReviewVO>> page(@RequestBody PageParam<HealthVideoQuery> param) {
        PageBean<HealthVideoReviewVO> pageBean = healthVideosService.pageQuery(param);
        return ResultBean.success(pageBean);
    }
    
    /**
     * 分页查询公开的健康视频（用户端接口）
     * @param param 分页参数和查询条件
     * @return 健康视频分页数据
     */
    @Operation(summary = "分页查询公开的健康视频（用户端）")
    @PostMapping("/public/page")
    public ResultBean<PageBean<HealthVideoVO>> publicPage(@RequestBody PageParam<HealthVideoQuery> param) {
        PageBean<HealthVideoVO> pageBean = healthVideosService.pagePublicVideos(param);
        return ResultBean.success(pageBean);
    }

    /**
     * 根据ID获取健康视频详情（管理端接口）
     * @param id 视频ID
     * @return 健康视频详情
     */
    @Operation(summary = "根据ID获取健康视频详情（管理端）")
    @GetMapping("/{id}/view")
    public ResultBean<HealthVideoVO> getHealthVideoById(@PathVariable Long id) {
        HealthVideoVO vo = healthVideosService.getHealthVideoById(id);
        if (vo == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "视频不存在或已被删除");
        }
        return ResultBean.success(vo);
    }
    
    /**
     * 根据ID获取公开的健康视频详情（用户端接口）
     * @param id 视频ID
     * @return 健康视频详情
     */
    @Operation(summary = "根据ID获取公开的健康视频详情（用户端）")
    @GetMapping("/public/{id}")
    public ResultBean<HealthVideoVO> getPublicHealthVideoById(@PathVariable Long id) {
        HealthVideoVO vo = healthVideosService.getPublicHealthVideoById(id);
        if (vo == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "视频不存在或未公开");
        }
        return ResultBean.success(vo);
    }

    /**
     * 创建健康视频
     * @param videoCreateDTO 视频创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建健康视频")
    @PostMapping("/create")
    public ResultBean<String> createHealthVideo(@Valid @RequestBody HealthVideoCreateDTO videoCreateDTO) {
        try {
            boolean saved = healthVideosService.createHealthVideo(videoCreateDTO);
            if (saved) {
                return ResultBean.success("视频创建成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "视频创建失败");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "视频创建过程中发生错误: " + e.getMessage());
        }
    }

    /**
     * 更新健康视频
     * @param videoUpdateDTO 视频更新信息
     * @return 操作结果
     */
    @Operation(summary = "更新健康视频")
    @PutMapping("/update")
    public ResultBean<String> updateHealthVideo(@Valid @RequestBody HealthVideoUpdateDTO videoUpdateDTO) {
        try {
            boolean updated = healthVideosService.updateHealthVideo(videoUpdateDTO);
            if (updated) {
                return ResultBean.success("视频更新成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "视频不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "视频更新过程中发生错误: " + e.getMessage());
        }
    }
    
    /**
     * 审核健康视频
     * @param id 视频ID
     * @param status 审核状态（0:草稿 1:已发布 2:已下架 3:审核中 4:未通过审核）
     * @return 操作结果
     */
    @Operation(summary = "审核健康视频")
    @PutMapping("/{id}/review")
    public ResultBean<String> reviewHealthVideo(@PathVariable Long id, @RequestParam Byte status) {
        try {
            // 验证状态值是否有效
            if (status < 0 || status > 4) {
                return ResultBean.fail(StatusCode.PARAMETER_ERROR, "无效的状态值，只允许设置为0(草稿)、1(已发布)、2(已下架)、3(审核中)或4(未通过审核)");
            }
            
            HealthVideoUpdateDTO videoUpdateDTO = new HealthVideoUpdateDTO();
            videoUpdateDTO.setId(id);
            videoUpdateDTO.setStatus(status);
            
            boolean updated = healthVideosService.updateHealthVideo(videoUpdateDTO);
            if (updated) {
                String statusText = switch (status) {
                    case 0 -> "已设为草稿";
                    case 1 -> "审核通过";
                    case 2 -> "已下架";
                    case 3 -> "设为审核中";
                    case 4 -> "审核不通过";
                    default -> "";
                };
                return ResultBean.success("视频" + statusText);
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "视频不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "视频审核过程中发生错误: " + e.getMessage());
        }
    }

    /**
     * 删除健康视频（逻辑删除）
     * @param id 视频ID
     * @return 操作结果
     */
    @Operation(summary = "删除健康视频")
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> deleteHealthVideo(@PathVariable Long id) {
        try {
            boolean deleted = healthVideosService.deleteHealthVideo(id);
            if (deleted) {
                return ResultBean.success("视频删除成功");
            } else {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "视频不存在或已被删除");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "视频删除过程中发生错误: " + e.getMessage());
        }
    }
}