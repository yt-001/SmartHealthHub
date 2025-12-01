package com.xitian.smarthealthhub.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类工具类
 */
public class CategoryUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 将分类ID列表转换为JSON字符串存储
     * @param categoryIds 分类ID列表
     * @return JSON字符串
     */
    public static String convertCategoryIdsToJson(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return "[]";
        }
        
        try {
            return objectMapper.writeValueAsString(categoryIds);
        } catch (JsonProcessingException e) {
            logger.error("将分类ID列表转换为JSON字符串时发生错误", e);
            return "[]";
        }
    }
    
    /**
     * 将存储的JSON字符串转换为分类ID列表
     * @param categoryJson JSON字符串
     * @return 分类ID列表
     */
    public static List<Long> parseCategoryIdsFromJson(String categoryJson) {
        if (categoryJson == null || categoryJson.isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            return objectMapper.readValue(categoryJson, new TypeReference<List<Long>>() {});
        } catch (JsonProcessingException e) {
            logger.error("解析分类ID列表JSON字符串时发生错误", e);
            return new ArrayList<>();
        }
    }
}