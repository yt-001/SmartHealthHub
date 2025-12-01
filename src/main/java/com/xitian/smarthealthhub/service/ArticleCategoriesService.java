package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.ArticleCategoriesCreateDTO;
import com.xitian.smarthealthhub.domain.dto.ArticleCategoriesUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.ArticleCategories;
import com.xitian.smarthealthhub.domain.query.ArticleCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.ArticleCategoriesVO;

import java.util.List;

/**
 * 文章分类服务接口
 */
public interface ArticleCategoriesService extends IService<ArticleCategories> {
    
    /**
     * 分页查询文章分类
     * @param param 分页参数和查询条件
     * @return 文章分类分页数据
     */
    PageBean<ArticleCategoriesVO> pageQuery(PageParam<ArticleCategoriesQuery> param);
    
    /**
     * 获取所有启用的文章分类
     * @return 文章分类列表
     */
    List<ArticleCategoriesVO> getAllEnabledCategories();
    
    /**
     * 根据ID获取文章分类详情
     * @param id 分类ID
     * @return 文章分类详情
     */
    ArticleCategoriesVO getArticleCategoryById(Long id);
    
    /**
     * 创建文章分类
     * @param articleCategoriesCreateDTO 文章分类创建信息
     * @return 操作结果
     */
    boolean createArticleCategory(ArticleCategoriesCreateDTO articleCategoriesCreateDTO);
    
    /**
     * 更新文章分类
     * @param articleCategoriesUpdateDTO 文章分类更新信息
     * @return 操作结果
     */
    boolean updateArticleCategory(ArticleCategoriesUpdateDTO articleCategoriesUpdateDTO);
    
    /**
     * 删除文章分类（逻辑删除）
     * @param id 分类ID
     * @return 操作结果
     */
    boolean deleteArticleCategory(Long id);
}