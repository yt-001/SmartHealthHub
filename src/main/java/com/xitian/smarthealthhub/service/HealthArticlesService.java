package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.HealthArticleCreateDTO;
import com.xitian.smarthealthhub.domain.dto.HealthArticleUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.HealthArticles;
import com.xitian.smarthealthhub.domain.query.HealthArticleQuery;
import com.xitian.smarthealthhub.domain.query.HealthArticlePublicQuery;
import com.xitian.smarthealthhub.domain.vo.HealthArticleVO;
import com.xitian.smarthealthhub.domain.vo.HealthArticleReviewVO;
import com.xitian.smarthealthhub.domain.vo.HealthArticleAdminVO;

import java.util.List;

/**
 * 健康文章服务接口
 */
public interface HealthArticlesService extends IService<HealthArticles> {
    
    /**
     * 分页查询健康文章（供管理端审核使用）
     * @param param 分页参数和查询条件
     * @return 健康文章分页数据
     */
    PageBean<HealthArticleReviewVO> pageQuery(PageParam<HealthArticleQuery> param);
    
    /**
     * 分页查询健康文章（供管理员使用，包含分类信息）
     * @param param 分页参数和查询条件
     * @return 健康文章分页数据（包含分类信息）
     */
    PageBean<HealthArticleAdminVO> pageQueryWithCategories(PageParam<HealthArticleQuery> param);
    
    /**
     * 分页查询公开的健康文章（供用户端使用）
     * @param param 分页参数和查询条件
     * @return 健康文章分页数据
     */
    PageBean<HealthArticleVO> pagePublicArticles(PageParam<HealthArticlePublicQuery> param);
    
    /**
     * 根据作者ID分页查询公开的健康文章
     * @param authorId 作者ID
     * @param param 分页参数
     * @return 健康文章分页数据
     */
    PageBean<HealthArticleVO> pagePublicArticlesByAuthorId(Long authorId, PageParam<Void> param);
    
    /**
     * 获取热门健康文章（按浏览量倒序）
     * @param limit 限制数量
     * @return 热门健康文章列表
     */
    List<HealthArticleVO> getTopHotArticles(int limit);

    /**
     * 根据ID获取健康文章详情
     * @param id 文章ID
     * @return 健康文章详情
     */
    HealthArticleVO getHealthArticleById(Long id);
    
    /**
     * 创建健康文章
     * @param articleCreateDTO 文章创建信息
     * @return 操作结果
     */
    boolean createHealthArticle(HealthArticleCreateDTO articleCreateDTO);
    
    /**
     * 更新健康文章
     * @param articleUpdateDTO 文章更新信息
     * @return 操作结果
     */
    boolean updateHealthArticle(HealthArticleUpdateDTO articleUpdateDTO);
    
    /**
     * 删除健康文章（逻辑删除）
     * @param id 文章ID
     * @return 操作结果
     */
    boolean deleteHealthArticle(Long id);
}