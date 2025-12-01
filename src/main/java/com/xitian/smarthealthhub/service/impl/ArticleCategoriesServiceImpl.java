package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.ArticleCategoriesConverter;
import com.xitian.smarthealthhub.converter.CategorySimpleConverter;
import com.xitian.smarthealthhub.domain.dto.ArticleCategoriesCreateDTO;
import com.xitian.smarthealthhub.domain.dto.ArticleCategoriesUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.ArticleCategories;
import com.xitian.smarthealthhub.domain.query.ArticleCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.ArticleCategoriesVO;
import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;
import com.xitian.smarthealthhub.mapper.ArticleCategoriesMapper;
import com.xitian.smarthealthhub.service.ArticleCategoriesService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章分类服务实现类
 */
@Service
public class ArticleCategoriesServiceImpl extends ServiceImpl<ArticleCategoriesMapper, ArticleCategories> implements ArticleCategoriesService {
    
    /**
     * 分页查询文章分类
     * @param param 分页参数和查询条件
     * @return 文章分类分页数据
     */
    @Override
    public PageBean<ArticleCategoriesVO> pageQuery(PageParam<ArticleCategoriesQuery> param) {
        // 创建分页对象
        Page<ArticleCategories> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<ArticleCategories> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            ArticleCategoriesQuery query = param.getQuery();
            
            // 分类名称模糊查询
            if (StringUtils.hasText(query.getName())) {
                queryWrapper.like(ArticleCategories::getName, query.getName());
            }
            
            // 是否启用精确查询
            if (query.getIsEnabled() != null) {
                queryWrapper.eq(ArticleCategories::getIsEnabled, query.getIsEnabled());
            }
        }
        
        // 按排序字段升序排列，再按创建时间倒序排列
        queryWrapper.orderByAsc(ArticleCategories::getSortOrder);
        queryWrapper.orderByDesc(ArticleCategories::getCreatedAt);
        
        // 执行分页查询
        Page<ArticleCategories> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<ArticleCategoriesVO> voList = ArticleCategoriesConverter.toVOList(resultPage.getRecords());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 获取所有启用的文章分类（简化信息）
     * @return 文章分类简化信息列表
     */
    @Override
    public List<CategorySimpleVO> getAllEnabledCategoriesSimple() {
        LambdaQueryWrapper<ArticleCategories> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ArticleCategories::getIsEnabled, (byte) 1);
        queryWrapper.orderByAsc(ArticleCategories::getSortOrder);
        List<ArticleCategories> categoriesList = this.list(queryWrapper);
        return CategorySimpleConverter.fromArticleCategoryList(categoriesList);
    }
    
    /**
     * 根据ID列表获取文章分类（简化信息）
     * @param ids 分类ID列表
     * @return 文章分类简化信息列表
     */
    @Override
    public List<CategorySimpleVO> getArticleCategoriesSimpleByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        
        LambdaQueryWrapper<ArticleCategories> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(ArticleCategories::getId, ids);
        queryWrapper.eq(ArticleCategories::getIsEnabled, (byte) 1);
        queryWrapper.orderByAsc(ArticleCategories::getSortOrder);
        List<ArticleCategories> categoriesList = this.list(queryWrapper);
        return CategorySimpleConverter.fromArticleCategoryList(categoriesList);
    }
    
    /**
     * 获取所有启用的文章分类
     * @return 文章分类列表
     */
    @Override
    public List<ArticleCategoriesVO> getAllEnabledCategories() {
        LambdaQueryWrapper<ArticleCategories> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ArticleCategories::getIsEnabled, (byte) 1);
        queryWrapper.orderByAsc(ArticleCategories::getSortOrder);
        List<ArticleCategories> categoriesList = this.list(queryWrapper);
        return ArticleCategoriesConverter.toVOList(categoriesList);
    }
    
    /**
     * 根据ID获取文章分类详情
     * @param id 分类ID
     * @return 文章分类详情
     */
    @Override
    public ArticleCategoriesVO getArticleCategoryById(Long id) {
        ArticleCategories articleCategory = this.getById(id);
        if (articleCategory == null) {
            return null;
        }
        return ArticleCategoriesConverter.toVO(articleCategory);
    }
    
    /**
     * 创建文章分类
     * @param articleCategoriesCreateDTO 文章分类创建信息
     * @return 操作结果
     */
    @Override
    public boolean createArticleCategory(ArticleCategoriesCreateDTO articleCategoriesCreateDTO) {
        ArticleCategories articleCategory = new ArticleCategories();
        articleCategory.setName(articleCategoriesCreateDTO.getName());
        articleCategory.setDescription(articleCategoriesCreateDTO.getDescription());
        articleCategory.setSortOrder(articleCategoriesCreateDTO.getSortOrder() != null ? articleCategoriesCreateDTO.getSortOrder() : 0);
        articleCategory.setIsEnabled(articleCategoriesCreateDTO.getIsEnabled() != null ? articleCategoriesCreateDTO.getIsEnabled() : (byte) 1);
        articleCategory.setCreatedAt(LocalDateTime.now());
        articleCategory.setUpdatedAt(LocalDateTime.now());
        
        return this.save(articleCategory);
    }
    
    /**
     * 更新文章分类
     * @param articleCategoriesUpdateDTO 文章分类更新信息
     * @return 操作结果
     */
    @Override
    public boolean updateArticleCategory(ArticleCategoriesUpdateDTO articleCategoriesUpdateDTO) {
        ArticleCategories articleCategory = this.getById(articleCategoriesUpdateDTO.getId());
        if (articleCategory == null) {
            return false;
        }
        
        articleCategory.setName(articleCategoriesUpdateDTO.getName());
        articleCategory.setDescription(articleCategoriesUpdateDTO.getDescription());
        articleCategory.setSortOrder(articleCategoriesUpdateDTO.getSortOrder() != null ? articleCategoriesUpdateDTO.getSortOrder() : articleCategory.getSortOrder());
        articleCategory.setIsEnabled(articleCategoriesUpdateDTO.getIsEnabled() != null ? articleCategoriesUpdateDTO.getIsEnabled() : articleCategory.getIsEnabled());
        articleCategory.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(articleCategory);
    }
    
    /**
     * 删除文章分类（物理删除）
     * @param id 分类ID
     * @return 操作结果
     */
    @Override
    public boolean deleteArticleCategory(Long id) {
        ArticleCategories articleCategory = this.getById(id);
        if (articleCategory == null) {
            return false;
        }
        
        return this.removeById(articleCategory.getId());
    }
}