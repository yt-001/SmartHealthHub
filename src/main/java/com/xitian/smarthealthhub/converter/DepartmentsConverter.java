package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.domain.vo.DepartmentsVO;

/**
 * 科室转换器
 */
public class DepartmentsConverter {
    
    /**
     * Entity -> VO
     * @param entity 科室实体
     * @return 科室VO
     */
    public static DepartmentsVO toVO(Departments entity) {
        if (entity == null) {
            return null;
        }

        DepartmentsVO vo = new DepartmentsVO();
        
        // BaseVO字段
        vo.setId(entity.getId());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        
        // 科室字段
        vo.setName(entity.getName());
        vo.setDescription(entity.getDescription());
        vo.setHeadDoctorId(entity.getHeadDoctorId());
        vo.setIsActive(entity.getIsActive());
        
        return vo;
    }
    
    /**
     * Entity -> VO (带关联信息)
     * @param entity 科室实体
     * @param headDoctorName 科室主任姓名
     * @return 科室VO
     */
    public static DepartmentsVO toVO(Departments entity, String headDoctorName) {
        DepartmentsVO vo = toVO(entity);
        if (vo != null) {
            vo.setHeadDoctorName(headDoctorName);
        }
        return vo;
    }
}