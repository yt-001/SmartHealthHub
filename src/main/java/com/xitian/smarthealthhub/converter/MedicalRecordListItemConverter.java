package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordListItemVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 病例列表项转换器
 */
public class MedicalRecordListItemConverter {
    
    /**
     * MedicalRecords Entity -> MedicalRecordListItemVO
     * @param entity MedicalRecords实体
     * @return MedicalRecordListItemVO
     */
    public static MedicalRecordListItemVO toVO(MedicalRecords entity) {
        if (entity == null) {
            return null;
        }
        
        MedicalRecordListItemVO vo = new MedicalRecordListItemVO();
        vo.setId(entity.getId());
        // 若诊断结果为空则展示主诉症状
        vo.setDiagnosis(entity.getDiagnosis() != null && !entity.getDiagnosis().isEmpty() 
                ? entity.getDiagnosis() 
                : entity.getSymptoms());
        vo.setDoctorName(entity.getDoctorName());
        vo.setVisitDate(entity.getVisitDate());
        vo.setStatus(entity.getStatus() != null ? entity.getStatus().intValue() : null);
        
        return vo;
    }
    
    /**
     * MedicalRecords Entity列表 -> MedicalRecordListItemVO列表
     * @param entityList MedicalRecords实体列表
     * @return MedicalRecordListItemVO列表
     */
    public static List<MedicalRecordListItemVO> toVOList(List<MedicalRecords> entityList) {
        if (entityList == null) {
            return null;
        }
        
        List<MedicalRecordListItemVO> voList = new ArrayList<>();
        for (MedicalRecords entity : entityList) {
            voList.add(toVO(entity));
        }
        return voList;
    }
}